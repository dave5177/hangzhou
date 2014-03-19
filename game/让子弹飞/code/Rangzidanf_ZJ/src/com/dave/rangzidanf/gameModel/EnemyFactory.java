package com.dave.rangzidanf.gameModel;

public class EnemyFactory {
	public static Enemy createEnemy(int ranks) {
		return new Enemy(ranks);
	}
}
