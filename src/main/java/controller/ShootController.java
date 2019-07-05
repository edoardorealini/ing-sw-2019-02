package controller;

import exception.NotAllowedMoveException;
import exception.NotAllowedShootingModeException;
import exception.NotAllowedTargetException;
import exception.NotEnoughAmmoException;
import model.Color;
import model.Match;
import model.ShootMode;
import model.ShootingParametersInput;
import model.map.Directions;
import model.map.Square;
import model.player.Player;
import model.weapons.Effect;
import model.weapons.Weapon;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the ShootController, an important class that rules all the shooting routine.
 * The idea is: check if everything the user wants to do is allowed and, only after that, execute the effects of the weapon
 * using {@link model.weapons.Effect#executeEffect(Match, MoveController, ShootingParametersInput)}.
 * @author Riccardo
 */

public class ShootController{

    //attributes

    private Match match;
    private MoveController moveController;
    private ShootingParametersInput input;
    private String paymentErrorMessage = "It seems you don't have enough ammo";
    private String notValidTarget = "The selected target is not valid";

    //getter methods

    public ShootController(Match match, MoveController moveController) {
        this.match = match;
        this.moveController = moveController;
        this.input = new ShootingParametersInput();
    }

    public Player getCurrPlayer() {
        return match.getCurrentPlayer();
    }

    /**
     * This method checks the visibility between two players.
     * @param player1 Reference to player 1
     * @param player2 Reference to player 2
     * @return True if player 1 can see player 2
     */

    private boolean visibilityBetweenPlayers(Player player1, Player player2) {
        return match.getMap().getVisibileRooms(player1.getPosition()).contains(player2.getPosition().getColor());
    }

    /**
     * This method checks if the player can pay ammo for the optional effects.
     * @param weapon The object of the weapon which the user wants to use
     * @param shootingModes The shooting modes chosen by the player.
     * @throws NotEnoughAmmoException Thrown only if the player doesn't have enough ammo
     */

     void checkPayAmmo(Weapon weapon, List<ShootMode> shootingModes) throws NotEnoughAmmoException {

        int r = 0;
        int b = 0;
        int y = 0;
        int actualRedAmmo = getCurrPlayer().getAmmo().getRedAmmo();           //ammo already owned by the current player
        int actualBlueAmmo = getCurrPlayer().getAmmo().getBlueAmmo();
        int actualYellowAmmo = getCurrPlayer().getAmmo().getYellowAmmo();

        for (ShootMode mode : shootingModes) {
            for (Color color : weapon.getModeCost(mode)) {
                if (color != null) {
                    switch (color) {
                        case RED:
                            r++;
                            break;
                        case BLUE:
                            b++;
                            break;
                        case YELLOW:
                            y++;
                            break;
                        default:
                            break;
                    }
                }
            }
        }


        if (actualRedAmmo - r < 0 || actualBlueAmmo - b < 0 || actualYellowAmmo - y < 0)
            throw new NotEnoughAmmoException(paymentErrorMessage);

    }

    /**
     * This method makes the player pay ammo for the optional effects
     * @param cost List of the colors of the ammo that needed to be paid
     */

    void payAmmo(List<Color> cost) {

        if (cost.isEmpty())
            return;

        int r = 0;
        int b = 0;
        int y = 0;

        for (Color color : cost) {
            if (color != null) {
                switch (color) {
                    case RED:
                        r++;
                        break;
                    case BLUE:
                        b++;
                        break;
                    case YELLOW:
                        y++;
                        break;
                    default:
                        break;
                }
            }
        }

        getCurrPlayer().removeAmmo(r, b, y);
    }

    /**
     * This method checks the correct visibility between two players depending on the requirements of the effect.
     * @param eff Reference to the effect that later will be executed
     * @param player1 Reference to player 1
     * @param player2 Reference to player 2
     * @throws NotAllowedTargetException Thrown if the player is not an allowed target depending on the visibiliy
     */

    private void checkCorrectVisibility(Effect eff, Player player1, Player player2) throws NotAllowedTargetException {
        //this method checks if the visibility required by the weapon is respected
        if (eff.getMoveTarget() != 0 || eff.getMoveYourself() != 0)
            return;
        if (eff.needVisibleTarget() != visibilityBetweenPlayers(player1, player2))
            throw new NotAllowedTargetException(notValidTarget);
    }

    /**
     * This method checks if the distance between player 1 and player 2 is bigger than the minShootDistance required by the effect
     * @param eff Reference to the effect that later will be executed
     * @param player1 Reference to player 1
     * @param player2 Reference to player 2
     * @throws NotAllowedTargetException Thrown if the distance between Player 1 and player 2 is too small
     */

    private void checkAllowedDistance(Effect eff, Player player1, Player player2) throws NotAllowedTargetException {
        //this method checks if the distance required by the weapon is respected
        if (moveController.minDistBetweenSquares(player1.getPosition(), player2.getPosition()) < eff.getMinShootDistance())
            throw new NotAllowedTargetException(notValidTarget);

    }

    /**
     * This method checks if the distance between player 1 and player 2 is equal to the minShootDistance required by the effect
     * @param eff Reference to the effect that later will be executed
     * @param player1 Reference to player 1
     * @param player2 Reference to player 2
     * @throws NotAllowedTargetException Thrown if the distance between Player 1 and player 2 is different from the minShootDistance required by the effect
     */

    private void checkExactDistance(Effect eff, Player player1, Player player2) throws NotAllowedTargetException {
        //this method checks if the distance required by the weapon is the same that separate the two players, ONLY FOR DAMAGE EFFECT
        int k;

        if (eff.getDamage() != 0) {

            if (eff.getMinShootDistance() == -1) {
                k = 0;
            } else {
                k = eff.getMinShootDistance();
            }

            if (moveController.minDistBetweenSquares(player1.getPosition(), player2.getPosition()) != k)
                throw new NotAllowedTargetException(notValidTarget);

        }
    }

    /**
     * This method checks if the distance between the player and the destination is a valid value.
     * @param eff Reference to the effect that later will be executed
     * @param player Reference to player that need to move
     * @param square Reference to the destination square
     * @throws NotAllowedMoveException Thrown if the destination is too far
     */

    private void checkMaximumDistance(Effect eff, Player player, Square square, int maxDistance) throws NotAllowedMoveException {
        //this method checks if the distance between the player and the square is smaller then the set parameter
        if (eff.getDamage() != 0 || eff.getMark() != 0)
            return;
        if (moveController.minDistBetweenSquares(player.getPosition(), square) > maxDistance)
            throw new NotAllowedMoveException("Move error during shoot");
    }

    /**
     * This method checks if it is an allowed move in a specific direction (walls cannot be passed).
     * @param player1 Reference to player
     * @param square  Reference to the destination square
     * @param direction Direction on which should stay the player and the destination square
     * @throws NotAllowedTargetException Thrown if the movement is not happening in only one direction
     */

    private void checkSameDirectionAllowed(Player player1, Square square, Directions direction) throws NotAllowedTargetException {
        if(direction != null) {
            if (match.getMap().getAllowedSquaresInDirection(direction, player1.getPosition()).contains(square)) {
                return;
            } else {
                throw new NotAllowedTargetException(notValidTarget);
            }
        }

        ArrayList<Directions> cardinalDirections = new ArrayList<>();
        cardinalDirections.add(Directions.UP);
        cardinalDirections.add(Directions.DOWN);
        cardinalDirections.add(Directions.LEFT);
        cardinalDirections.add(Directions.RIGHT);

        for (Directions dir: cardinalDirections) {
            if (match.getMap().getAllowedSquaresInDirection(dir, player1.getPosition()).contains(square))  //condition verified if the two squares are not on the same line
                return;
        }

        throw new NotAllowedTargetException(notValidTarget);
    }

    /**
     * This method checks if it is an allowed move in a specific direction (walls can be passed).
     * @param player1 Reference to player
     * @param square  Reference to the destination square
     * @param direction Direction on which should stay the player and the destination square
     * @throws NotAllowedTargetException Thrown if the movement is not happening in only one direction
     */

    private void checkSameDirectionThroughWalls(Player player1, Square square, Directions direction) throws NotAllowedTargetException {
        //this method checks if the square and the position of the player are on the same line (don't care about the walls)
        if(direction != null) {
            if (match.getMap().getAllSquaresInDirection(direction, player1.getPosition()).contains(square)) {
                return;
            } else {
                throw new NotAllowedTargetException(notValidTarget);
            }
        }

        ArrayList<Directions> cardinalDirections = new ArrayList<>();
        cardinalDirections.add(Directions.UP);
        cardinalDirections.add(Directions.DOWN);
        cardinalDirections.add(Directions.LEFT);
        cardinalDirections.add(Directions.RIGHT);

        for (Directions dir: cardinalDirections) {
            if (match.getMap().getAllSquaresInDirection(dir, player1.getPosition()).contains(square))  //condition verified if the two squares are not on the same line
                return;
        }

        throw new NotAllowedTargetException(notValidTarget);
    }

    /**
     * This method checks if two squares are in the same room.
     * @param s1 Square 1
     * @param s2 Square 2
     * @throws NotAllowedTargetException Thrown if the two squares are not in the same room
     */

    private void checkDifferentRoom(Square s1, Square s2) throws NotAllowedTargetException {
        if (s1.getColor() == s2.getColor())
            throw new NotAllowedTargetException(notValidTarget);
    }

    /**
     * This method sets the field input, instance of {@link ShootingParametersInput}, which will be used in every method of this class.
     * It will be filled with parameters received from the client.
     * @param inputGui Input coming from the GUI
     */

    public void setInput (ShootingParametersInput inputGui) {
        //this method allows me to copy the input parameters received from the client
        input.setWeapon(inputGui.getWeapon());
        input.setDirection(inputGui.getDirection());
        input.setMakeDamageBeforeMove(inputGui.getMakeDamageBeforeMove());
        input.getTargets().clear();
        input.getSquares().clear();
        input.getShootModes().clear();
        for (Player player : inputGui.getTargets()) {
            input.setTargets(player);
        }
        for (Square square : inputGui.getSquares()) {
            input.setSquares(square);
        }
        for (ShootMode mode : inputGui.getShootModes()) {
            input.setShootModes(mode);
        }
    }


    //shoot methods

    /**
     * This is only used for Lock Rifle: it checks if the input parameters are valid and, eventually, execute the effect of the weapon.
     * @throws NotAllowedTargetException Thrown if the chosen target is not a valid option
     * @throws NotEnoughAmmoException Thrown if the user does not have enough ammo to pay the optional effects
     */

    public void shootLockRifle () throws NotAllowedTargetException, NotEnoughAmmoException {
        //this method is valid only for LOCK RIFLE

        //the first cycle check if the effects can be applied
        for (ShootMode mode : input.getShootModes()) {
            for (Effect eff : input.getWeapon().getMode(mode)) {
                try {
                    checkCorrectVisibility(eff, getCurrPlayer(), input.getTargets().get(eff.getIndexOfTarget()));
                } catch (NotAllowedTargetException e) {
                    e.printStackTrace();
                    throw new NotAllowedTargetException(notValidTarget);
                }
            }
        }

        try {
            checkPayAmmo(input.getWeapon(), input.getShootModes());
        } catch (NotEnoughAmmoException e) {
            throw new NotEnoughAmmoException(paymentErrorMessage);
        }


        for (ShootMode mode : input.getShootModes()) {
            payAmmo(input.getWeapon().getModeCost(mode));
        }

        //execution cycle
        for (ShootMode mode : input.getShootModes()) {
            for (Effect eff : input.getWeapon().getMode(mode)) {
                try {
                    eff.executeEffect(match, moveController, input);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * This is only used for Electroscythe: it checks if the input parameters are valid and, eventually, execute the effect of the weapon.
     * @throws NotAllowedTargetException Thrown if the chosen target is not a valid option
     * @throws NotEnoughAmmoException Thrown if the user does not have enough ammo to pay the optional effects
     */

    public void shootElectroScythe () throws NotAllowedTargetException, NotEnoughAmmoException {
        //this method is valid only for ELECTRO SCYTHE

        Effect eff;
        input.getTargets().clear();
        ShootMode mode = input.getShootModes().get(0);
        eff = input.getWeapon().getMode(mode).get(0);   //take the effect


        for (Player player: match.getPlayers()) {
            if(player.getId() != getCurrPlayer().getId() && player.getPosition() == getCurrPlayer().getPosition()) {
                input.getTargets().add(player);
            }
        }

        if (mode.equals(ShootMode.ALTERNATE))
            try {
                checkPayAmmo(input.getWeapon(), input.getShootModes());
                payAmmo(input.getWeapon().getModeCost(mode));
            } catch (NotEnoughAmmoException e) {
                throw new NotEnoughAmmoException(paymentErrorMessage);
            }

        try {
            eff.executeEffect(match, moveController, input);
        } catch (Exception e){
            throw new NotAllowedTargetException(notValidTarget);
        }

    }

    /**
     * This is only used for Machine Gun: it checks if the input parameters are valid and, eventually, execute the effect of the weapon.
     * @throws NotAllowedTargetException Thrown if the chosen target is not a valid option
     * @throws NotEnoughAmmoException Thrown if the user does not have enough ammo to pay the optional effects
     * @throws NotAllowedMoveException Thrown if there a problem in {@link model.weapons.Effect#executeEffect(Match, MoveController, ShootingParametersInput)}
     */

    public void shootMachineGun () throws NotAllowedTargetException, NotEnoughAmmoException, NotAllowedMoveException {
        //this method is valid only for MACHINE GUN

        for (ShootMode mode : input.getShootModes()) {
            for (Effect eff : input.getWeapon().getMode(mode)) {
                if (eff.getIndexOfTarget()<input.getTargets().size()) {	//check if the user has set more than one target
                    try {
                        checkCorrectVisibility(eff, getCurrPlayer(), input.getTargets().get(eff.getIndexOfTarget()));
                    } catch (NotAllowedTargetException e) {
                        e.printStackTrace();
                        throw new NotAllowedTargetException(notValidTarget);
                    }
                }
            }
        }

        try {
            checkPayAmmo(input.getWeapon(), input.getShootModes());
        } catch (NotEnoughAmmoException e) {
            throw new NotEnoughAmmoException(paymentErrorMessage);
        }

        for (ShootMode mode : input.getShootModes()) {
            payAmmo(input.getWeapon().getModeCost(mode));
        }

        for (ShootMode mode : input.getShootModes()) {
            for (Effect eff : input.getWeapon().getMode(mode)) {
                if (eff.getIndexOfTarget()<input.getTargets().size()) {    //check if the user has set more than one target
                    try {
                        eff.executeEffect(match, moveController, input);
                    } catch (Exception e) {
                        throw new NotAllowedMoveException("Move error during shoot");
                    }
                }
            }
        }

    }

    /**
     * This is only used for T.H.O.R.: it checks if the input parameters are valid and, eventually, execute the effect of the weapon.
     * @throws NotAllowedTargetException Thrown if the chosen target is not a valid option
     * @throws NotEnoughAmmoException Thrown if the user does not have enough ammo to pay the optional effects
     * @throws NotAllowedShootingModeException Thrown if there a problem in {@link model.weapons.Effect#executeEffect(Match, MoveController, ShootingParametersInput)}
     */

    public void shootTHOR () throws NotAllowedTargetException, NotEnoughAmmoException, NotAllowedShootingModeException {
        //this method is valid only for T.H.O.R.
        Effect eff;
        input.getTargets().add(0, getCurrPlayer());

        if (!input.getShootModes().get(0).equals(ShootMode.BASIC))
            throw new NotAllowedShootingModeException();

        if (input.getShootModes().size()==2 && !input.getShootModes().get(1).equals(ShootMode.OPTIONAL1))
            throw new NotAllowedShootingModeException();

        if (input.getShootModes().size()==3 && !input.getShootModes().get(2).equals(ShootMode.OPTIONAL2))
            throw new NotAllowedShootingModeException();

        for (ShootMode mode : input.getShootModes()) {
            eff = input.getWeapon().getMode(mode).get(0);
            try {
                checkCorrectVisibility(eff, input.getTargets().get(eff.getIndexOfTarget()-1), input.getTargets().get(eff.getIndexOfTarget()));
            } catch (NotAllowedTargetException e) {
                e.printStackTrace();
                throw new NotAllowedTargetException(notValidTarget);
            }
        }

        try {
            checkPayAmmo(input.getWeapon(), input.getShootModes());
        } catch (NotEnoughAmmoException e) {
            throw new NotEnoughAmmoException(paymentErrorMessage);
        }


        for (ShootMode mode : input.getShootModes()) {
            payAmmo(input.getWeapon().getModeCost(mode));
        }


        for (ShootMode mode : input.getShootModes()) {
            eff = input.getWeapon().getMode(mode).get(0);
            try {
                eff.executeEffect(match, moveController, input);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * This is only used for Plasma Gun: it checks if the input parameters are valid and, eventually, execute the effect of the weapon.
     * @throws NotAllowedTargetException Thrown if the chosen target is not a valid option
     * @throws NotEnoughAmmoException Thrown if the user does not have enough ammo to pay the optional effects
     * @throws NotAllowedMoveException Thrown if there a problem in {@link model.weapons.Effect#executeEffect(Match, MoveController, ShootingParametersInput)}
     */

    public void shootPlasmaGun () throws NotAllowedTargetException, NotEnoughAmmoException, NotAllowedMoveException {
        //this method is valid only for Plasma Gun
        Effect eff;
        Square squareTemp = getCurrPlayer().getPosition();

        if (input.getShootModes().get(0) == ShootMode.OPTIONAL1) {
            eff = input.getWeapon().getOptionalModeOne().get(0);
            checkMaximumDistance(eff, getCurrPlayer(), input.getSquares().get(0), eff.getMoveYourself());
            eff.executeEffect(match, moveController, input);
        }

        for (ShootMode mode : input.getShootModes()) {
            eff = input.getWeapon().getMode(mode).get(0);
            try {
                checkCorrectVisibility(eff, getCurrPlayer(), input.getTargets().get(eff.getIndexOfTarget()));
                checkMaximumDistance(eff, getCurrPlayer(), input.getSquares().get(0), eff.getMoveYourself());
            } catch (NotAllowedTargetException e) {
                e.printStackTrace();
                getCurrPlayer().setPosition(squareTemp);
                throw new NotAllowedTargetException(notValidTarget);
            } catch (NotAllowedMoveException e) {
                getCurrPlayer().setPosition(squareTemp);
                throw new NotAllowedMoveException("Move error during shoot");
            }
        }


        try {
            checkPayAmmo(input.getWeapon(), input.getShootModes());
        } catch (NotEnoughAmmoException e) {
            getCurrPlayer().setPosition(squareTemp);
            throw new NotEnoughAmmoException(paymentErrorMessage);
        }


        for (ShootMode mode : input.getShootModes()) {
            payAmmo(input.getWeapon().getModeCost(mode));
        }

        for (ShootMode mode : input.getShootModes()) {
            eff = input.getWeapon().getMode(mode).get(0);
            try {
                eff.executeEffect(match, moveController, input);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * This is only used for Whisper: it checks if the input parameters are valid and, eventually, execute the effect of the weapon.
     * @throws NotAllowedTargetException Thrown if the chosen target is not a valid option
     */

    public void shootWhisper () throws NotAllowedTargetException {
        //this method is valid only for Whisper

        try {
            for (Effect eff : input.getWeapon().getBasicMode()) {
                checkCorrectVisibility(eff, getCurrPlayer(), input.getTargets().get(eff.getIndexOfTarget()));
                checkAllowedDistance(eff, getCurrPlayer(), input.getTargets().get(eff.getIndexOfTarget()));
                eff.executeEffect(match, moveController, input);
            }
        } catch(Exception e) {
            throw new NotAllowedTargetException(notValidTarget);
        }

    }

    /**
     * This is only used for Tractor Beam: it checks if the input parameters are valid and, eventually, execute the effect of the weapon.
     * @throws NotAllowedTargetException Thrown if the chosen target is not a valid option
     * @throws NotEnoughAmmoException Thrown if the user does not have enough ammo to pay the optional effects
     * @throws NotAllowedMoveException Thrown if there a problem in {@link model.weapons.Effect#executeEffect(Match, MoveController, ShootingParametersInput)}
     */

    public void shootTractorBeam () throws NotAllowedTargetException, NotEnoughAmmoException, NotAllowedMoveException{
        //this method is valid only for Tractor Beam
        ShootMode mode = input.getShootModes().get(0);
        Square squareTemp = input.getTargets().get(0).getPosition();

        for (Effect eff : input.getWeapon().getMode(mode)) {
            try {
                checkCorrectVisibility(eff, getCurrPlayer(), input.getTargets().get(eff.getIndexOfTarget()));
                checkMaximumDistance(eff, input.getTargets().get(0), input.getSquares().get(0), eff.getMoveTarget());
                if (mode.equals(ShootMode.ALTERNATE)) {
                    checkMaximumDistance(eff, input.getTargets().get(0), input.getSquares().get(0), eff.getMoveTarget());
                    checkExactDistance(eff, getCurrPlayer(), input.getTargets().get(0));
                }
                if (eff.getDamage() == 0)
                    eff.executeEffect(match, moveController, input);
            } catch (NotAllowedTargetException e) {
                e.printStackTrace();
                input.getTargets().get(0).setPosition(squareTemp);
                throw new NotAllowedTargetException(notValidTarget);
            }  catch (NotAllowedMoveException e) {
                input.getTargets().get(0).setPosition(squareTemp);
                throw new NotAllowedMoveException("Move error during shoot");
            }
        }

        if (mode.equals(ShootMode.ALTERNATE)) {
            try {
                checkPayAmmo(input.getWeapon(), input.getShootModes());
                payAmmo(input.getWeapon().getModeCost(mode));
            } catch (NotEnoughAmmoException e) {
                input.getTargets().get(0).setPosition(squareTemp);
                throw new NotEnoughAmmoException(paymentErrorMessage);
            }
        }

        for (Effect eff : input.getWeapon().getMode(mode)) {
            try {
                eff.executeEffect(match, moveController, input);
            } catch (NotAllowedMoveException e) {
                input.getTargets().get(0).setPosition(squareTemp);
                throw new NotAllowedMoveException("Move error during shoot");
            }
        }

    }

    /**
     * This is only used for Cannon Vortex: it checks if the input parameters are valid and, eventually, execute the effect of the weapon.
     * @throws NotAllowedTargetException Thrown if the chosen target is not a valid option
     * @throws NotEnoughAmmoException Thrown if the user does not have enough ammo to pay the optional effects
     * @throws NotAllowedMoveException Thrown if there a problem in {@link model.weapons.Effect#executeEffect(Match, MoveController, ShootingParametersInput)}
     */

    public void shootCannonVortex () throws NotAllowedTargetException, NotEnoughAmmoException, NotAllowedMoveException {
        //this method is valid only for Cannon Vortex
        Square tempSquare1 = input.getTargets().get(0).getPosition();
        Square tempSquare2 = null;
        Square tempSquare3 = null;

        if (input.getTargets().size() > 1)
            tempSquare2 = input.getTargets().get(1).getPosition();
        if (input.getTargets().size() > 2)
            tempSquare3 = input.getTargets().get(2).getPosition();

        if (input.getTargets().get(0) != null)
            moveController.move(input.getTargets().get(0), input.getSquares().get(0), 1);
        if (input.getTargets().size() > 1)
            moveController.move(input.getTargets().get(1), input.getSquares().get(0), 1);
        if (input.getTargets().size() > 2)
            moveController.move(input.getTargets().get(2), input.getSquares().get(0), 1);


        for (ShootMode mode : input.getShootModes()) {
            for (Effect eff : input.getWeapon().getMode(mode)) {
                if (eff.getIndexOfTarget() < input.getTargets().size()) {	//check if the user has set more than one target
                    try {
                        checkMaximumDistance(eff, input.getTargets().get(eff.getIndexOfTarget()), input.getSquares().get(0), eff.getMoveTarget());
                        checkCorrectVisibility(eff, getCurrPlayer(), input.getTargets().get(eff.getIndexOfTarget()));
                        checkAllowedDistance(eff, getCurrPlayer(), input.getTargets().get(eff.getIndexOfTarget()));
                    } catch (NotAllowedTargetException e) {
                        input.getTargets().get(0).setPosition(tempSquare1);
                        if (input.getTargets().size() > 1)
                            input.getTargets().get(1).setPosition(tempSquare2);
                        if (input.getTargets().size() > 2)
                            input.getTargets().get(2).setPosition(tempSquare3);
                        throw new NotAllowedTargetException(notValidTarget);
                    } catch (NotAllowedMoveException e) {
                        input.getTargets().get(0).setPosition(tempSquare1);
                        if (input.getTargets().size() > 1)
                            input.getTargets().get(1).setPosition(tempSquare2);
                        if (input.getTargets().size() > 2)
                            input.getTargets().get(2).setPosition(tempSquare3);
                        throw new NotAllowedMoveException("Move error during shoot");
                    }
                }
            }
        }


        try {
            checkPayAmmo(input.getWeapon(), input.getShootModes());
        } catch (NotEnoughAmmoException e) {
            input.getTargets().get(0).setPosition(tempSquare1);
            if (input.getTargets().size() > 1)
                input.getTargets().get(1).setPosition(tempSquare2);
            if (input.getTargets().size() > 2)
                input.getTargets().get(2).setPosition(tempSquare3);
            throw new NotEnoughAmmoException(paymentErrorMessage);
        }

        for (ShootMode mode : input.getShootModes()) {
            payAmmo(input.getWeapon().getModeCost(mode));
        }

        for (ShootMode mode : input.getShootModes()) {
            for (Effect eff : input.getWeapon().getMode(mode)) {
                try {
                    eff.executeEffect(match, moveController, input);
                } catch (Exception e){
                    input.getTargets().get(0).setPosition(tempSquare1);
                    if (input.getTargets().size() > 1)
                        input.getTargets().get(1).setPosition(tempSquare2);
                    if (input.getTargets().size() > 2)
                        input.getTargets().get(2).setPosition(tempSquare3);
                }
            }
        }

    }

    /**
     * This is only used for Furnace: it checks if the input parameters are valid and, eventually, execute the effect of the weapon.
     * @throws NotAllowedTargetException Thrown if the chosen target is not a valid option
     * @throws NotAllowedShootingModeException Thrown if the chosen mode is not valid
     * @throws NotAllowedMoveException Thrown if there a problem in {@link model.weapons.Effect#executeEffect(Match, MoveController, ShootingParametersInput)}
     */

    public void shootFurnace () throws NotAllowedTargetException, NotAllowedMoveException, NotAllowedShootingModeException {
        //this method is valid only for Furnace

        ShootMode mode = input.getShootModes().get(0);
        Effect eff;
        input.getTargets().clear();



        switch (mode) {

            case BASIC:

                for (Player player : match.getPlayers()) { 				//here the correct targets are set
                    if (player.getId() != getCurrPlayer().getId() && player.getPosition().getColor() == input.getSquares().get(0).getColor()) {
                        input.getTargets().add(player);
                    }
                }

                try {
                    checkDifferentRoom(getCurrPlayer().getPosition(), input.getSquares().get(0));
                } catch (NotAllowedTargetException e) {
                    throw new NotAllowedTargetException(notValidTarget);
                }

                eff = input.getWeapon().getMode(mode).get(0);

                for (Player player : input.getTargets()) {
                    checkCorrectVisibility(eff, getCurrPlayer(), player);
                }

                eff.executeEffect(match, moveController, input);

                break;

            case ALTERNATE:

                for (Player player : match.getPlayers()) { 				//here the correct targets are set
                    if (player.getId() != getCurrPlayer().getId() && player.getPosition() == input.getSquares().get(0)) {
                        input.getTargets().add(player);
                    }
                }

                for (Player player : input.getTargets()) {
                    for (Effect effect: input.getWeapon().getMode(mode)) {
                        try {
                            checkExactDistance(effect, getCurrPlayer(), player);
                            checkCorrectVisibility(effect, getCurrPlayer(), player);
                        } catch (NotAllowedTargetException e){
                            throw new NotAllowedTargetException(notValidTarget);
                        }
                    }
                }

                for (Effect effect : input.getWeapon().getMode(mode)) {
                    try {
                        effect.executeEffect(match, moveController, input);
                    } catch (Exception e) {
                        throw new NotAllowedMoveException("Move error during shoot");
                    }
                }

                break;

                default:
                    throw new NotAllowedShootingModeException("Not allowed shooting mode");
        }
    }

    /**
     * This is only used for T.H.O.R.: it checks if the input parameters are valid and, eventually, execute the effect of the weapon.
     * @throws NotAllowedTargetException Thrown if the chosen target is not a valid option
     */

    public void shootHeatseeker () throws NotAllowedTargetException {
        //this method is valid only for Heatseeker

        try {
            Effect eff = input.getWeapon().getBasicMode().get(0);
            checkCorrectVisibility(eff, getCurrPlayer(), input.getTargets().get(eff.getIndexOfTarget()));
            eff.executeEffect(match, moveController, input);
        } catch(Exception e) {
            throw new NotAllowedTargetException(notValidTarget);
        }

    }

    /**
     * This is only used for Hellion: it checks if the input parameters are valid and, eventually, execute the effect of the weapon.
     * @throws NotAllowedTargetException Thrown if the chosen target is not a valid option
     * @throws NotEnoughAmmoException Thrown if the user does not have enough ammo to pay the optional effects
     */

    public void shootHellion () throws NotAllowedTargetException, NotEnoughAmmoException {
        //this method in valid only for Hellion
        ShootMode mode = input.getShootModes().get(0);

        for (Player player : match.getPlayers()) {  		//add the players in the same square to target list
            if (input.getTargets().get(0).getPosition() == player.getPosition() && player.getId() != getCurrPlayer().getId() && player.getId() != input.getTargets().get(0).getId())
                input.getTargets().add(player);
        }

        for (Effect eff : input.getWeapon().getMode(mode)) {
            for (Player player : input.getTargets()) {
                checkCorrectVisibility(eff, getCurrPlayer(), player);
                checkAllowedDistance(eff, getCurrPlayer(), player);
            }
        }


        if (mode.equals(ShootMode.ALTERNATE)) {
            try {
                checkPayAmmo(input.getWeapon(), input.getShootModes());
                payAmmo(input.getWeapon().getModeCost(mode));
            } catch (NotEnoughAmmoException e) {
                throw new NotEnoughAmmoException(paymentErrorMessage);
            }
        }

        for (Effect eff : input.getWeapon().getMode(mode)) {
            try {
                eff.executeEffect(match, moveController, input);
            } catch (NotAllowedMoveException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * This is only used for FlameThrower: it checks if the input parameters are valid and, eventually, execute the effect of the weapon.
     * @throws NotAllowedTargetException Thrown if the chosen target is not a valid option
     * @throws NotEnoughAmmoException Thrown if the user does not have enough ammo to pay the optional effects
     * @throws NotAllowedShootingModeException Thrown if the chosen mode is not valid
     */

    public void shootFlameThrower () throws NotAllowedTargetException, NotEnoughAmmoException, NotAllowedShootingModeException {
        //this method is valid only for FlameThrower

        ShootMode mode = input.getShootModes().get(0);
        input.getSquares().clear();

        //with the next two if I set the squares in which the user want to make damages
        if (match.getMap().getAllowedSquaresInDirection(input.getDirection(), getCurrPlayer().getPosition()).size()>1) {
            Square sq1 = match.getMap().getAllowedSquaresInDirection(input.getDirection(), getCurrPlayer().getPosition()).get(1);
            input.setSquares(sq1);
        }
        if (match.getMap().getAllowedSquaresInDirection(input.getDirection(), getCurrPlayer().getPosition()).size()>2) {
            Square sq2 = match.getMap().getAllowedSquaresInDirection(input.getDirection(), getCurrPlayer().getPosition()).get(2);
            input.setSquares(sq2);
        }


        switch (mode) {

            case BASIC:

                for (Effect eff : input.getWeapon().getMode(mode)) {
                    if (input.getTargets().size() < eff.getIndexOfTarget()) {
                        checkExactDistance(eff, getCurrPlayer(), input.getTargets().get(eff.getIndexOfTarget()));
                        if (!input.getSquares().contains(input.getTargets().get(eff.getIndexOfTarget()).getPosition()))
                            throw new NotAllowedTargetException(notValidTarget);
                    }
                }
                for (Effect eff : input.getWeapon().getMode(mode)) {
                    try {
                        eff.executeEffect(match, moveController, input);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;

            case ALTERNATE:

                try {
                    checkPayAmmo(input.getWeapon(), input.getShootModes());
                    payAmmo(input.getWeapon().getModeCost(mode));
                } catch (NotEnoughAmmoException e) {
                    throw new NotEnoughAmmoException(paymentErrorMessage);
                }

                //execution code
                input.getTargets().clear();
                for (Player player : match.getPlayers()) {
                    if (input.getSquares().get(0).equals(player.getPosition()) && !player.getNickname().equals(match.getCurrentPlayer().getNickname()))
                        input.setTargets(player);
                }
                try {
                    input.getWeapon().getMode(mode).get(0).executeEffect(match, moveController, input);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (input.getSquares().size() > 1) {

                    input.getTargets().clear();
                    for (Player player : match.getPlayers()) {
                        if (input.getSquares().get(1).equals(player.getPosition()) && !player.getNickname().equals(match.getCurrentPlayer().getNickname()))
                            input.setTargets(player);
                    }
                    try {
                        input.getWeapon().getMode(mode).get(1).executeEffect(match, moveController, input);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;

            case OPTIONAL1:
                throw new NotAllowedShootingModeException();

            case OPTIONAL2:
                throw new NotAllowedShootingModeException();
        }

    }

    /**
     * This is only used for Grenade Launcher: it checks if the input parameters are valid and, eventually, execute the effect of the weapon.
     * @throws NotAllowedTargetException Thrown if the chosen target is not a valid option
     * @throws NotEnoughAmmoException Thrown if the user does not have enough ammo to pay the optional effects
     * @throws NotAllowedMoveException Thrown if there a problem in {@link model.weapons.Effect#executeEffect(Match, MoveController, ShootingParametersInput)}
     */

    public void shootGrenadeLauncher () throws NotAllowedTargetException, NotEnoughAmmoException, NotAllowedMoveException {
        //this method is valid only for Grenade Launcher
        Player mainTarget = input.getTargets().get(0);
        Square moveHerePlayer = input.getSquares().get(0);
        Square bombHere = input.getSquares().get(1);

        for (ShootMode mode : input.getShootModes()) {
            for (Effect eff : input.getWeapon().getMode(mode)) {
                try {
                    checkMaximumDistance(eff, mainTarget, moveHerePlayer, eff.getMoveTarget());
                    checkCorrectVisibility(eff, getCurrPlayer(), mainTarget);
                } catch (NotAllowedMoveException e) {
                    throw new  NotAllowedMoveException("Move error during shoot");
                }
            }
        }

        try {
            checkPayAmmo(input.getWeapon(), input.getShootModes());
        } catch (NotEnoughAmmoException e) {
            throw new NotEnoughAmmoException(paymentErrorMessage);
        }

        for (ShootMode mode : input.getShootModes()) {
            payAmmo(input.getWeapon().getModeCost(mode));
        }


        if (input.getMakeDamageBeforeMove()) {  //if the player wants to hit every player in the square before moving the target
            input.getWeapon().getBasicMode().get(0).executeEffect(match, moveController, input);
            input.getTargets().clear();
            for (Player player : match.getPlayers()) {
                if (player.getPosition() == bombHere && player.getId() != getCurrPlayer().getId())
                    input.setTargets(player);
            }
            if (input.getShootModes().contains(ShootMode.OPTIONAL1))
                input.getWeapon().getOptionalModeOne().get(0).executeEffect(match, moveController, input);
            input.getTargets().clear();
            input.setTargets(mainTarget);
            input.getWeapon().getBasicMode().get(1).executeEffect(match, moveController, input);
        } else {            //damage mainTarget, them move it, then damage everyone
            for (ShootMode mode : input.getShootModes()) {
                for (Effect eff : input.getWeapon().getMode(mode)) {
                    if (mode == ShootMode.OPTIONAL1) {
                        input.getTargets().clear();
                        for (Player player : match.getPlayers()) {
                            if (player.getPosition() == bombHere && player.getId() != getCurrPlayer().getId())
                                input.setTargets(player);
                        }
                    }
                    eff.executeEffect(match, moveController, input);
                }
            }
        }
    }

    /**
     * This is only used for Rocket Launcher: it checks if the input parameters are valid and, eventually, execute the effect of the weapon.
     * @throws NotAllowedTargetException Thrown if the chosen target is not a valid option
     * @throws NotEnoughAmmoException Thrown if the user does not have enough ammo to pay the optional effects
     * @throws NotAllowedMoveException Thrown if there a problem in {@link model.weapons.Effect#executeEffect(Match, MoveController, ShootingParametersInput)}
     */

    public void shootRocketLauncher () throws NotAllowedTargetException, NotEnoughAmmoException, NotAllowedMoveException {
        //this method is valid only for Rocket Launcher
        //REMEMBER, in input.squares(0) there is the position of the target player after the basic effect,
        // while in input.square(1) there is the final position of the curr player
         Effect eff;
         Square moveTargetHere = input.getSquares().get(0);
         Square moveYourselfHere = input.getSquares().get(1);
         Square squareTemp = getCurrPlayer().getPosition();

         if (input.getShootModes().get(0) == ShootMode.OPTIONAL1) {
             eff = input.getWeapon().getOptionalModeOne().get(0);
             checkMaximumDistance(eff, getCurrPlayer(), moveYourselfHere, eff.getMoveYourself());
             input.getSquares().clear();
             input.setSquares(moveYourselfHere);
             eff.executeEffect(match, moveController, input);
         }

         for (ShootMode mode : input.getShootModes()) {
             for (Effect effect : input.getWeapon().getMode(mode)) {
                 try {
                     checkCorrectVisibility(effect, getCurrPlayer(), input.getTargets().get(0));
                     checkAllowedDistance(effect, getCurrPlayer(), input.getTargets().get(0));
                     if (mode.equals(ShootMode.BASIC))
                        checkMaximumDistance(effect, input.getTargets().get(0), moveTargetHere, effect.getMoveTarget());
                     if (mode.equals(ShootMode.OPTIONAL1))
                        checkMaximumDistance(effect, getCurrPlayer(), moveYourselfHere, effect.getMoveYourself());
                 } catch (NotAllowedTargetException e) {
                     e.printStackTrace();
                     getCurrPlayer().setPosition(squareTemp);
                     throw new NotAllowedTargetException(notValidTarget);
                 } catch (NotAllowedMoveException e) {
                     getCurrPlayer().setPosition(squareTemp);
                     throw new NotAllowedMoveException("Move error during shoot");
                 }
             }

         }


         try {
             checkPayAmmo(input.getWeapon(), input.getShootModes());
         } catch (NotEnoughAmmoException e) {
             getCurrPlayer().setPosition(squareTemp);
             throw new NotEnoughAmmoException(paymentErrorMessage);
         }

        for (ShootMode mode : input.getShootModes()) {
            payAmmo(input.getWeapon().getModeCost(mode));
        }

        if (input.getShootModes().contains(ShootMode.OPTIONAL2)) {   //Adding the players that have to be damaged by optional 2 effect
            for (Player player : match.getPlayers()) {
                if (player.getId() != getCurrPlayer().getId() && player.getId() != input.getTargets().get(0).getId() && player.getPosition() == input.getTargets().get(0).getPosition())
                    input.setTargets(player);
            }
        }

         for (ShootMode mode : input.getShootModes()) {
             for (Effect effect : input.getWeapon().getMode(mode)) {
                 try {
                     if (mode.equals(ShootMode.OPTIONAL1)) {
                         input.getSquares().clear();
                         input.setSquares(moveYourselfHere);
                     }
                     if (mode.equals(ShootMode.BASIC)) {
                         input.getSquares().clear();
                         input.setSquares(moveTargetHere);
                     }
                     effect.executeEffect(match, moveController, input);
                 } catch (Exception e) {
                     e.printStackTrace();
                 }
             }
         }
    }

    /**
     * This is only used for RailGun: it checks if the input parameters are valid and, eventually, execute the effect of the weapon.
     * @throws NotAllowedTargetException Thrown if the chosen target is not a valid option
     */

    public void shootRailGun () throws NotAllowedTargetException {
        //this method is valid only for Rail Gun

        ShootMode mode = input.getShootModes().get(0);
        input.getSquares().clear();

        for (Effect eff : input.getWeapon().getMode(mode)) {
            try {
                checkSameDirectionThroughWalls(getCurrPlayer(), input.getTargets().get(eff.getIndexOfTarget()).getPosition(), input.getDirection());
            } catch (NotAllowedTargetException e) {
                e.printStackTrace();
                throw new NotAllowedTargetException(notValidTarget);
            }
        }

        for (Effect eff : input.getWeapon().getMode(mode)) {
            try {
                eff.executeEffect(match, moveController, input);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * This is only used for Cyber Blade: it checks if the input parameters are valid and, eventually, execute the effect of the weapon.
     * @throws NotAllowedTargetException Thrown if the chosen target is not a valid option
     * @throws NotEnoughAmmoException Thrown if the user does not have enough ammo to pay the optional effects
     * @throws NotAllowedMoveException Thrown if there a problem in {@link model.weapons.Effect#executeEffect(Match, MoveController, ShootingParametersInput)}
     */

    public void shootCyberBlade() throws NotAllowedTargetException, NotAllowedMoveException, NotEnoughAmmoException {
        //this method is valid only for CyberBlade
        Effect eff;
        Square squareTemp = getCurrPlayer().getPosition();


        for (ShootMode mode : input.getShootModes()) {
            eff = input.getWeapon().getMode(mode).get(0);
            try {
                if (mode == ShootMode.OPTIONAL1) {
                    checkMaximumDistance(eff, getCurrPlayer(), input.getSquares().get(0), eff.getMoveYourself());
                    eff.executeEffect(match, moveController, input);
                }
                checkCorrectVisibility(eff, getCurrPlayer(), input.getTargets().get(eff.getIndexOfTarget()));
                checkExactDistance(eff, getCurrPlayer(), input.getTargets().get(eff.getIndexOfTarget()));
            } catch (NotAllowedTargetException e) {
                e.printStackTrace();
                getCurrPlayer().setPosition(squareTemp);
                throw new NotAllowedTargetException(notValidTarget);
            } catch (NotAllowedMoveException e) {
                getCurrPlayer().setPosition(squareTemp);
                throw new NotAllowedMoveException("Move error during shoot");
            }
        }

        try {
            checkPayAmmo(input.getWeapon(), input.getShootModes());
        } catch (NotEnoughAmmoException e) {
            getCurrPlayer().setPosition(squareTemp);
            throw new NotEnoughAmmoException(paymentErrorMessage);
        }

        for (ShootMode mode : input.getShootModes()) {
            payAmmo(input.getWeapon().getModeCost(mode));
        }

        getCurrPlayer().setPosition(squareTemp);      //reset the player to his initial position

        for (ShootMode mode : input.getShootModes()) {
            eff = input.getWeapon().getMode(mode).get(0);
            try {
                eff.executeEffect(match, moveController, input);
            } catch (NotAllowedMoveException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * This is only used for ZX-2: it checks if the input parameters are valid and, eventually, execute the effect of the weapon.
     * @throws NotAllowedTargetException Thrown if the chosen target is not a valid option
     * @throws NotAllowedMoveException Thrown if there a problem in {@link model.weapons.Effect#executeEffect(Match, MoveController, ShootingParametersInput)}
     */

    public void shootZX2 () throws NotAllowedTargetException, NotAllowedMoveException {
        //this method is valid only for ZX2
        ShootMode mode = input.getShootModes().get(0);

        for (Effect eff : input.getWeapon().getMode(mode)) {
            try {
                checkCorrectVisibility(eff, getCurrPlayer(), input.getTargets().get(eff.getIndexOfTarget()));
            } catch (NotAllowedTargetException e) {
                e.printStackTrace();
                throw new NotAllowedTargetException(notValidTarget);
            }
        }

        for (Effect eff : input.getWeapon().getMode(mode)) {
            try {
              eff.executeEffect(match, moveController, input);
            } catch (NotAllowedMoveException e) {
                throw new NotAllowedMoveException("Move error during shoot");
            }
        }
    }

    /**
     * This is only used for Shotgun: it checks if the input parameters are valid and, eventually, execute the effect of the weapon.
     * @throws NotAllowedTargetException Thrown if the chosen target is not a valid option
     * @throws NotAllowedMoveException Thrown if there a problem in {@link model.weapons.Effect#executeEffect(Match, MoveController, ShootingParametersInput)}
     */

    public void shootShotgun () throws NotAllowedTargetException, NotAllowedMoveException {
        //this method is valid only for Shotgun

        ShootMode mode = input.getShootModes().get(0);

        for (Effect eff : input.getWeapon().getMode(mode)) {
            try {
                checkCorrectVisibility(eff, getCurrPlayer(), input.getTargets().get(0));
                checkMaximumDistance(eff, input.getTargets().get(0), input.getSquares().get(0), eff.getMoveTarget());
                checkExactDistance(eff, getCurrPlayer(), input.getTargets().get(0));
            } catch (NotAllowedTargetException e) {
                e.printStackTrace();
                throw new NotAllowedTargetException(notValidTarget);
            } catch (NotAllowedMoveException e) {
                throw new NotAllowedMoveException("Move error during shoot");
            }
        }

        for (Effect eff : input.getWeapon().getMode(mode)) {
            try {
                eff.executeEffect(match, moveController, input);
            } catch (NotAllowedMoveException e) {
                throw new NotAllowedMoveException("Move error during shoot");
            }
        }
    }

    /**
     * This is only used for Power Glove: it checks if the input parameters are valid and, eventually, execute the effect of the weapon.
     * @throws NotAllowedTargetException Thrown if the chosen target is not a valid option
     * @throws NotEnoughAmmoException Thrown if the user does not have enough ammo to pay the optional effects
     * @throws NotAllowedMoveException Thrown if there a problem in {@link model.weapons.Effect#executeEffect(Match, MoveController, ShootingParametersInput)}
     */

    public void shootPowerGlove () throws NotAllowedTargetException, NotAllowedMoveException, NotEnoughAmmoException {
        //this method is valid only for Power Glove

        Directions dir = input.getDirection();
        ShootMode mode = input.getShootModes().get(0);
        Square squareTemp = getCurrPlayer().getPosition();
        Square sq2;
        Square sq1;
        int i = 0;
        input.getSquares().clear();

        if (! getCurrPlayer().getPosition().getAllowedMoves().contains(dir))
            throw new NotAllowedMoveException("Move error during shoot");

        if (match.getMap().getAllowedSquaresInDirection(dir, getCurrPlayer().getPosition()).size() > 1) {
            sq1 = match.getMap().getAllowedSquaresInDirection(input.getDirection(), getCurrPlayer().getPosition()).get(1);
            input.setSquares(sq1);
        }
        if (match.getMap().getAllowedSquaresInDirection(dir, getCurrPlayer().getPosition()).size() > 2 && mode == ShootMode.ALTERNATE) {
            sq2 = match.getMap().getAllowedSquaresInDirection(input.getDirection(), getCurrPlayer().getPosition()).get(2);
            input.setSquares(sq2);
        }

        int k = input.getSquares().size();

        for (Effect eff : input.getWeapon().getMode(mode)) {
            try {
                if (i == 3 && input.getTargets().size() < 2)
                    break;
                checkCorrectVisibility(eff, getCurrPlayer(), input.getTargets().get(eff.getIndexOfTarget()));
                checkExactDistance(eff, getCurrPlayer(), input.getTargets().get(eff.getIndexOfTarget()));
                if (eff.getMoveYourself() != 0) {
                   if (i == 2 && input.getSquares().size() > 1)
                       input.getSquares().remove(0);
                   eff.executeEffect(match, moveController, input);
                }
                i++;
            } catch (NotAllowedTargetException e) {
                e.printStackTrace();
                getCurrPlayer().setPosition(squareTemp);
                throw new NotAllowedTargetException(notValidTarget);
            } catch (NotAllowedMoveException e) {
                getCurrPlayer().setPosition(squareTemp);
                e.printStackTrace();
                throw new NotAllowedMoveException("Move error during shoot");
            }
        }

        if (mode == ShootMode.ALTERNATE) {
            try {
                checkPayAmmo(input.getWeapon(), input.getShootModes());
                payAmmo(input.getWeapon().getModeCost(mode));
            } catch (NotEnoughAmmoException e) {
                getCurrPlayer().setPosition(squareTemp);
                throw new NotEnoughAmmoException(paymentErrorMessage);
            }
        }

        i = 0;

        for (Effect eff : input.getWeapon().getMode(mode)) {
            try {
                if ((k == 1 || input.getTargets().size() < 2) && i == 3)
                    return;
                if (eff.getMoveYourself() == 0)
                    eff.executeEffect(match, moveController, input);
                i++;
            } catch (NotAllowedMoveException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * This is only used for Schock Wave: it checks if the input parameters are valid and, eventually, execute the effect of the weapon.
     * @throws NotAllowedTargetException Thrown if the chosen target is not a valid option
     * @throws NotEnoughAmmoException Thrown if the user does not have enough ammo to pay the optional effects
     * @throws NotAllowedMoveException Thrown if there a problem in {@link model.weapons.Effect#executeEffect(Match, MoveController, ShootingParametersInput)}
     */

    public void shootSchockWave () throws NotAllowedTargetException, NotEnoughAmmoException, NotAllowedMoveException {
        //this method is valid only for Shockwave
        ShootMode mode = input.getShootModes().get(0);

        if (mode == ShootMode.ALTERNATE) {
            input.getTargets().clear();
            for (Player player : match.getPlayers()) {
                if (moveController.minDistBetweenSquares(getCurrPlayer().getPosition(), player.getPosition()) == 1)
                    input.setTargets(player);
            }
        }

        for (Effect eff : input.getWeapon().getMode(mode)) {
            if (input.getTargets().size() > eff.getIndexOfTarget() && mode != ShootMode.ALTERNATE) {
                try {
                    checkCorrectVisibility(eff, getCurrPlayer(), input.getTargets().get(eff.getIndexOfTarget()));
                    checkExactDistance(eff, getCurrPlayer(), input.getTargets().get(eff.getIndexOfTarget()));
                } catch (NotAllowedTargetException e) {
                    e.printStackTrace();
                    throw new NotAllowedTargetException(notValidTarget);
                }
            }
        }

        if (mode == ShootMode.ALTERNATE) {
            try {
                checkPayAmmo(input.getWeapon(), input.getShootModes());
                payAmmo(input.getWeapon().getModeCost(mode));
            } catch (NotEnoughAmmoException e) {
                throw new NotEnoughAmmoException(paymentErrorMessage);
            }
        }

        if (mode == ShootMode.BASIC) {
            for (int i = 0; i < input.getTargets().size() - 1; i++) {
                if (input.getTargets().get(i).getPosition() == input.getTargets().get(i + 1).getPosition())
                    throw new NotAllowedTargetException(notValidTarget);
            }
            if (input.getTargets().size() == 3 && input.getTargets().get(0).getPosition() == input.getTargets().get(2).getPosition())
                throw new NotAllowedTargetException(notValidTarget);
        }

        for (Effect eff : input.getWeapon().getMode(mode)) {
           try {
               if (input.getTargets().size() > eff.getIndexOfTarget() && mode != ShootMode.ALTERNATE)
                   eff.executeEffect(match, moveController, input);
               if (mode == ShootMode.ALTERNATE)
                   eff.executeEffect(match, moveController, input);
           } catch (NotAllowedMoveException e) {
               throw new NotAllowedMoveException("Move error during shoot");
           }
        }
    }

    /**
     * This is only used for SledgeHammer: it checks if the input parameters are valid and, eventually, execute the effect of the weapon.
     * @throws NotAllowedTargetException Thrown if the chosen target is not a valid option
     * @throws NotEnoughAmmoException Thrown if the user does not have enough ammo to pay the optional effects
     * @throws NotAllowedMoveException Thrown if there a problem in {@link model.weapons.Effect#executeEffect(Match, MoveController, ShootingParametersInput)}
     */

    public void shootSledgehammer () throws NotAllowedTargetException, NotEnoughAmmoException, NotAllowedMoveException {
        //this method is valid only for Sledgehammer
        ShootMode mode = input.getShootModes().get(0);

        for (Effect eff : input.getWeapon().getMode(mode)) {
            try {
                checkCorrectVisibility(eff, getCurrPlayer(), input.getTargets().get(0));
                checkExactDistance(eff, getCurrPlayer(), input.getTargets().get(0));
                checkMaximumDistance(eff, getCurrPlayer(), input.getSquares().get(0), eff.getMoveTarget());
            } catch (NotAllowedTargetException e) {
                e.printStackTrace();
                throw new NotAllowedTargetException(notValidTarget);
            } catch (NotAllowedMoveException e) {
                throw new NotAllowedMoveException("Move error during shoot");
            }
        }

        if (mode == ShootMode.ALTERNATE)
            try {
                checkSameDirectionAllowed(getCurrPlayer(), input.getSquares().get(0), null);
            } catch (Exception e) {
                throw new NotAllowedMoveException("Move error during shoot");
            }

        if (mode == ShootMode.ALTERNATE) {
            try {
                checkPayAmmo(input.getWeapon(), input.getShootModes());
                payAmmo(input.getWeapon().getModeCost(mode));
            } catch (NotEnoughAmmoException e) {
                throw new NotEnoughAmmoException(paymentErrorMessage);
            }
        }

        for (Effect eff : input.getWeapon().getMode(mode)) {
            try {
                eff.executeEffect(match, moveController, input);
            } catch (NotAllowedMoveException e) {
                throw new NotAllowedMoveException("Move error during shoot");
            }
        }
    }

}
