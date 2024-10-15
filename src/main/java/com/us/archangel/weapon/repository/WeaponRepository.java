package com.us.archangel.weapon.repository;

import com.us.archangel.weapon.entity.WeaponEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class WeaponRepository {

    private static WeaponRepository instance;

    public static WeaponRepository getInstance(SessionFactory sessionFactory) {
        if (instance == null) {
            instance = new WeaponRepository(sessionFactory);
        }
        return instance;
    }

    public static WeaponRepository getInstance() {
        if (instance == null) {
            throw new RuntimeException("WeaponRepository has not been initialized");
        }
        return instance;
    }

    private final SessionFactory sessionFactory;

    private WeaponRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void create(WeaponEntity weapon) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(weapon); // Persist the entity
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public void updateById(int id, WeaponEntity updatedWeapon) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            WeaponEntity weapon = session.get(WeaponEntity.class, id);
            if (weapon != null) {
                weapon.setDisplayName(updatedWeapon.getDisplayName());
                weapon.setUniqueName(updatedWeapon.getUniqueName());
                weapon.setType(updatedWeapon.getType());
                weapon.setMinDamage(updatedWeapon.getMinDamage());
                weapon.setMaxDamage(updatedWeapon.getMaxDamage());
                weapon.setRangeInTiles(updatedWeapon.getRangeInTiles());
                weapon.setAccuracy(updatedWeapon.getAccuracy());
                weapon.setReloadTime(updatedWeapon.getReloadTime());
                weapon.setReloadMessage(updatedWeapon.getReloadMessage());
                weapon.setAmmoCapacity(updatedWeapon.getAmmoCapacity());
                weapon.setWeight(updatedWeapon.getWeight());
                weapon.setCooldownSeconds(updatedWeapon.getCooldownSeconds());
                weapon.setSpecialAbilities(updatedWeapon.getSpecialAbilities());
                weapon.setEquipEffect(updatedWeapon.getEquipEffect());
                weapon.setEquipMessage(updatedWeapon.getEquipMessage());
                weapon.setUnequipMessage(updatedWeapon.getUnequipMessage());
                weapon.setAttackMessage(updatedWeapon.getAttackMessage());
                session.update(weapon); // Update the entity
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public WeaponEntity getById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(WeaponEntity.class, id);
        }
    }

    @SuppressWarnings("unchecked")
    public List<WeaponEntity> getAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from WeaponEntity").list();
        }
    }

    public void deleteById(int id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            WeaponEntity weapon = session.get(WeaponEntity.class, id);
            if (weapon != null) {
                session.delete(weapon);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }
}
