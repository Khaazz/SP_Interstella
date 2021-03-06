package core.utility;

import core.objects.entities.spaceships.enemies.AEnemy;
import core.objects.entities.spaceships.enemies.Enemy1;
import core.objects.entities.spaceships.enemies.Enemy2;
import core.objects.PowerUpRate;
import core.objects.configObject.EnemyConfig;

/**
 * Instantiate enemies regarding their type
 * Eventually buff them with the given PowerUp
 */
public class Instantiator {

    /**
     * Dynamically create an AEnemy regardingthe type to create
     * @param enemyConf
     * @return AEnemy
     */
    public static AEnemy createEnemy(EnemyConfig enemyConf) {
        switch (enemyConf.getType()) {
            case "enemy1": {
                return new Enemy1(enemyConf);
            }
            case "enemy2": {
                return new Enemy2(enemyConf);
            }
            default: {
                return new Enemy1(enemyConf);
            }
        }
    }

    /**
     * Dynamically create an AEnemy regardingthe type to create
     *
     * @param enemyConf
     * @param power
     * @return AEnemy
     */
    public static AEnemy createEnemy(EnemyConfig enemyConf, PowerUpRate power) {
        switch (enemyConf.getType()) {
            case "enemy1": {
                return new Enemy1(enemyConf, power);
            }
            case "enemy2": {
                return new Enemy2(enemyConf, power);
            }
            default: {
                return new Enemy1(enemyConf, power);
            }
        }
    }
}
