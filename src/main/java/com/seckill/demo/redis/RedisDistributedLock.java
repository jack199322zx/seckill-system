package com.seckill.demo.redis;

/**
 * @author ss
 * @date 2018/7/23 13:53
 */

import com.seckill.demo.utils.RedisUtil;
import redis.clients.jedis.Jedis;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;

/**
 * 基于Redis的SETNX操作实现的分布式锁
 *
 * 获取锁时最好用lock(long time, TimeUnit unit), 以免网路问题而导致线程一直阻塞
 */
public class RedisDistributedLock extends AbstractLock {

    private Jedis jedis;

    // 锁的名字
    protected String lockKey;
    // 锁的有效时长(毫秒)
    protected long lockExpires;

    private RedisDistributedLock(Jedis jedis, String lockKey, long lockExpires) {
        this.jedis = jedis;
        this.lockKey = lockKey;
        this.lockExpires = lockExpires;
    }

    public static RedisDistributedLock getInstance(String lockKey, long lockExpires) {
        return new RedisDistributedLock(RedisUtil.getJedis(), lockKey, lockExpires);
    }

    protected boolean lock(boolean useTimeout, long time, TimeUnit unit, boolean interrupt) throws InterruptedException {
        if (interrupt) {
            checkInterruption();
        }

        long start = System.currentTimeMillis();
        long timeout = unit.toMillis(time); // if !useTimeout, then it's useless

        while (!useTimeout || isTimeout(start, timeout)) {
            if (interrupt) {
                checkInterruption();
            }
            long lockExpireTime = System.currentTimeMillis() + lockExpires + 1;// 锁超时时间
            String stringOfLockExpireTime = String.valueOf(lockExpireTime);

            System.out.println("before setnx.........");
            if (jedis.setnx(lockKey, stringOfLockExpireTime) == 1) { // 获取到锁
                System.out.println("setnx..........");
                //成功获取到锁, 设置相关标识
                locked = true;
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }

            System.out.println("step setnx.........");
            String value = jedis.get(lockKey);
            if (value != null && isTimeExpired(value)) { // lock is expired
                System.out.println("value is expired");
                // 假设多个线程(非单jvm)同时走到这里
                String oldValue = jedis.getSet(lockKey, stringOfLockExpireTime); //原子操作
                // 但是走到这里时每个线程拿到的oldValue肯定不可能一样(因为getset是原子性的)
                // 加入拿到的oldValue依然是expired的，那么就说明拿到锁了
                if (oldValue != null && isTimeExpired(oldValue)) {
                    System.out.println(Thread.currentThread().getName() + ":flush expire-----" + oldValue);
                    //成功获取到锁, 设置相关标识
                    locked = true;
                    setExclusiveOwnerThread(Thread.currentThread());
                    return true;
                }
            } else {
                // TODO lock is not expired, enter next loop retrying
                locked = false;
            }
        }
        System.out.println("the lock is not expired");
        return false;
    }

    public boolean tryLock() {
        long lockExpireTime = System.currentTimeMillis() + lockExpires + 1;// 锁超时时间
        String stringOfLockExpireTime = String.valueOf(lockExpireTime);

        if (jedis.setnx(lockKey, stringOfLockExpireTime) == 1) { // 获取到锁
            // 成功获取到锁, 设置相关标识
            locked = true;
            setExclusiveOwnerThread(Thread.currentThread());
            return true;
        }

        String value = jedis.get(lockKey);
        if (value != null && isTimeExpired(value)) { // lock is expired
            // 假设多个线程(非单jvm)同时走到这里
            String oldValue = jedis.getSet(lockKey, stringOfLockExpireTime); //原子操作
            // 但是走到这里时每个线程拿到的oldValue肯定不可能一样(因为getset是原子性的)
            // 假如拿到的oldValue依然是expired的，那么就说明拿到锁了
            if (oldValue != null && isTimeExpired(oldValue)) {
                //成功获取到锁, 设置相关标识
                locked = true;
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
        } else {
            // TODO lock is not expired, enter next loop retrying
            locked = false;
        }

        return false;
    }

    public void clearJedis() {
        try {
            jedis.del(this.lockKey);
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            jedis.close();
        }
    }
    /**
     * Queries if this lock is held by any thread.
     *
     * @return {@code true} if any thread holds this lock and {@code false}
     * otherwise
     */
    public boolean isLocked() {
        return locked;
    }

    private void checkInterruption() throws InterruptedException {
        if (Thread.currentThread().isInterrupted()) {
            throw new InterruptedException();
        }
    }

    private boolean isTimeExpired(String value) {
        return Long.parseLong(value) < System.currentTimeMillis();
    }

    private boolean isTimeout(long start, long timeout) {
        return start + timeout > System.currentTimeMillis();
    }

    public Condition newCondition() {
        // TODO Auto-generated method stub
        return null;
    }

}
