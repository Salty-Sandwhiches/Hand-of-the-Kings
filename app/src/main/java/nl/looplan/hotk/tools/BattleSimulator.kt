package nl.looplan.hotk.tools

import java.util.Random
import kotlin.math.roundToInt

object BattleSimulator {

    data class Result(val attackingNumber: Int,
                      val defendingNumber: Int,
                      val attackingBonus: Double,
                      val defendingBonus: Double,
                      val remainingAttackers : Int,
                      val remainingDefenders : Int,
                      val armyDeltaFactor : Double,
                      val bonusFactor : Double,
                      val iterations : List<Iteration>) {

        data class Iteration(val randomNumber : Int,
                             val chanceForAttackersToWin : Double)
    }

    private const val STANDARD_WIN_CHANCE_ATTACKERS = 55

    private const val POINT_OF_ALGORITHM_CHANGE : Int = 10

    @JvmStatic
    fun run(attackingNumber: Int, defendingNumber : Int, attackingBonus : Double, defendingBonus : Double) : Result {

        // Assign the remaining attackers.
        var remainingAttackers : Int = attackingNumber

        // Assign the remaining defenders.
        var remainingDefenders : Int = defendingNumber

        // Define the army delta factor.
        val armyDeltaFactor: Double

        // Calculate the bonus for use in the algorithm.
        val algorithmAttackBonus = attackingBonus / 10
        val algorithmDefendBonus = defendingBonus / 10

        // Calculate the bonus factor, by dividing attack bonus by the defend bonus.
        val bonusFactor = (5 + algorithmAttackBonus) / (5 + algorithmDefendBonus)

        // Check if the total number of troops exceeds the point where the algorithm changes.
        if (attackingNumber + defendingNumber > POINT_OF_ALGORITHM_CHANGE) {
            armyDeltaFactor = (defendingNumber.toDouble() / attackingNumber.toDouble())
        } else {
            armyDeltaFactor = (attackingNumber - defendingNumber).toDouble()
        }

        // Calculate the half the amount of attackers.
        val halfOfAttackers = (0.5 * attackingNumber).roundToInt()

        // Assign random object.
        val random = Random()

        // Assign iterations list for holding iterations.
        val iterations : ArrayList<Result.Iteration> = ArrayList()

        // Loop while
        // - there are defenders left.
        // - there are more than 1 attackers.
        // - the amount of attackers is more than the half of the original amount of attackers.
        while (
            remainingDefenders > 0 &&
            remainingAttackers > 1 &&
            remainingAttackers > halfOfAttackers
            ) {

            // Generate random number between 1 - 100.
            val randomNumber = random.nextInt(100) + 1

            // The chance for the attackers to win.
            var chanceForAttackersToWin : Double

            if(attackingBonus > defendingBonus) {
                val chanceForDefendersToWin = -(1/bonusFactor) * ( (1/3) * armyDeltaFactor + STANDARD_WIN_CHANCE_ATTACKERS)
                chanceForAttackersToWin = 100 - chanceForDefendersToWin
            } else {
                chanceForAttackersToWin = 2 * STANDARD_WIN_CHANCE_ATTACKERS * bonusFactor - bonusFactor * ( (1/3) * armyDeltaFactor + STANDARD_WIN_CHANCE_ATTACKERS)
            }

            // Check if the attack power is below 5, if so set it to five to ensure change to win.
            if(chanceForAttackersToWin < 5) {
                chanceForAttackersToWin = 5.0
            } else
                // Check if the attack power above 95, if so set it to 95 to ensure change to win.
                if (chanceForAttackersToWin > 95) {
                chanceForAttackersToWin = 95.0
            }

            // When the random number is larger then the chance to win.
            if (randomNumber > chanceForAttackersToWin) {
                // The attacker lose a unit.
                remainingAttackers -= 1
            } else {
                // The defenders lose a unit.
                remainingDefenders -= 1
            }

            iterations.add(Result.Iteration(randomNumber, chanceForAttackersToWin))
        }

        // Return the result of the simulation.
        return Result(
            attackingNumber,
            defendingNumber,
            attackingBonus,
            defendingBonus,
            remainingAttackers,
            remainingDefenders,
            armyDeltaFactor,
            bonusFactor,
            iterations)
    }

}