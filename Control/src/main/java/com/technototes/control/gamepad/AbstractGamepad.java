package com.technototes.control.gamepad;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.technototes.control.Periodic;

//a class to base gamepads off of
public abstract class AbstractGamepad<T extends GamepadButton, U extends GamepadAxis> implements Periodic {
    //normal gamepad
    private Gamepad gamepad;
    //buttons
    public T a, b, x, y, start, back, leftBumper, rightBumper,
            dpadUp, dpadDown, dpadLeft, dpadRight, leftStickButton, rightStickButton;
    //axis
    public U leftTrigger, rightTrigger, leftStickX, leftStickY, rightStickX, rightStickY;
    //sticks
    public GamepadStick<U, T> leftStick, rightStick;
    //dpad
    public GamepadDpad<T> dpad;
    //periodics to run
    private Periodic[] periodics;

    public Class<T> buttonClass;
    public Class<U> axisClass;

    public AbstractGamepad(Gamepad g, Class<T> bClass, Class<U> aClass){
        gamepad = g;
        buttonClass = bClass;
        axisClass = aClass;
        try {
            setComponents(g);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        leftStick = new GamepadStick<U, T>(leftStickX, leftStickY, leftStickButton);
        rightStick = new GamepadStick<U, T>(rightStickX, rightStickY, rightStickButton);
        dpad = new GamepadDpad<T>(dpadUp, dpadDown, dpadLeft, dpadRight);
        periodics = new Periodic[]{a, b, x, y, start, back, leftBumper, rightBumper,
                leftTrigger, rightTrigger, leftStick, rightStick, dpad};
    }
    //to actually instantiate the objects

    public void setComponents(Gamepad g) throws InstantiationException, IllegalAccessException {

        //buttons
        a = buttonClass.newInstance();
        a.setSupplier(() -> g.a);
        b = buttonClass.newInstance();
        b.setSupplier(() -> g.b);
        x = buttonClass.newInstance();
        x.setSupplier(() -> g.x);
        y = buttonClass.newInstance();
        y.setSupplier(() -> g.y);

        start = buttonClass.newInstance();
        start.setSupplier(() -> g.start);
        back = buttonClass.newInstance();
        back.setSupplier(() -> g.back);

        //bumpers
        leftBumper = buttonClass.newInstance();
        leftBumper.setSupplier(() -> g.left_bumper);
        rightBumper = buttonClass.newInstance();
        rightBumper.setSupplier(() -> g.right_bumper);

        //dpad
        dpadUp = buttonClass.newInstance();
        dpadUp.setSupplier(() -> g.dpad_up);
        dpadDown = buttonClass.newInstance();
        dpadDown.setSupplier(() -> g.dpad_up);
        dpadLeft = buttonClass.newInstance();
        dpadLeft.setSupplier(() -> g.dpad_left);
        dpadRight = buttonClass.newInstance();
        dpadRight.setSupplier(() -> g.dpad_right);

        //left stick
        leftStickX = axisClass.newInstance();
        leftStickX.setSupplier(() -> g.left_stick_x);
        leftStickY = axisClass.newInstance();
        leftStickY.setSupplier(() -> g.left_stick_y);
        leftStickButton = buttonClass.newInstance();
        leftStickButton.setSupplier(() -> g.left_stick_button);

        //right stick
        rightStickX = axisClass.newInstance();
        rightStickX.setSupplier(() -> g.right_stick_x);
        rightStickY = axisClass.newInstance();
        rightStickY.setSupplier(() -> g.right_stick_y);
        rightStickButton = buttonClass.newInstance();
        rightStickButton.setSupplier(() -> g.right_stick_button);

        //triggers
        leftTrigger = axisClass.newInstance();
        leftTrigger.setSupplier(() -> g.left_trigger);
        rightTrigger = axisClass.newInstance();
        rightTrigger.setSupplier(() -> g.right_trigger);

    }

    //enums
    public enum Button{
        A, B, X, Y, START, BACK, LEFT_BUMPER, RIGHT_BUMPER, LEFT_STICK_BUTTON, RIGHT_STICK_BUTTON;
    }

    public enum Axis{
        LEFT_STICK_X, LEFT_STICK_Y, RIGHT_STICK_X, RIGHT_STICK_Y, LEFT_TRIGGER, RIGHT_TRIGGER;
    }


    public T getButton(Button bu){
        switch (bu){
            case A:
                return a;
            case B:
                return b;
            case X:
                return x;
            case Y:
                return y;
            case BACK:
                return back;
            case START:
                return start;
            case LEFT_BUMPER:
                return leftBumper;
            case RIGHT_BUMPER:
                return rightBumper;
            case LEFT_STICK_BUTTON:
                return leftStickButton;
            case RIGHT_STICK_BUTTON:
                return rightStickButton;
            default:
                return null;
        }
    }

    public U getAxis(Axis as){
        switch (as){
            case LEFT_STICK_X:
                return leftStickX;
            case LEFT_STICK_Y:
                return leftStickY;
            case RIGHT_STICK_X:
                return rightStickX;
            case RIGHT_STICK_Y:
                return rightStickY;
            case LEFT_TRIGGER:
                return leftTrigger;
            case RIGHT_TRIGGER:
                return rightTrigger;
            default:
                return null;
        }
    }


    public boolean getButtonAsBoolean(Button bu){
        return getButton(bu).getAsBoolean();
    }
    public double getAxisAsDouble(Axis as){
        return getAxis(as).getAsDouble();
    }
    public boolean getAxisAsBoolean(Axis as){
        return getAxis(as).getAsBoolean();
    }

    public GamepadStick<U, T> getLeftStick(){
        return leftStick;
    }

    public GamepadStick<U, T> getRightStick(){
        return rightStick;
    }

    public GamepadDpad<T> getDpad(){
        return dpad;
    }

    @Override
    public void periodic() {
        for(Periodic p : periodics){
            p.periodic();
        }
    }

    public Gamepad getGamepad(){
        return gamepad;
    }
}
