package enamel;

import java.util.ArrayList;
import java.util.List;
import com.pi4j.wiringpi.Gpio;
import com.pi4j.wiringpi.GpioInterruptCallback;

public class TactilePlayer extends Player {

	List<HWButton> HWButtonList;

	int ON = 0;
	int DOUT = 1;
	int STROBE = 2;
	int CLOCK = 3;
	private long debounce = 0;

	public TactilePlayer(int brailleCellNumber, int ButtonNumber) {

		super(brailleCellNumber, ButtonNumber);

		try {
			Gpio.pinMode(ON, Gpio.OUTPUT);
			Gpio.pinMode(DOUT, Gpio.OUTPUT);
			Gpio.pinMode(STROBE, Gpio.OUTPUT);
			Gpio.pinMode(CLOCK, Gpio.OUTPUT);
			Gpio.digitalWrite(ON, 1);
			Gpio.delay(2000);
			Gpio.digitalWrite(ON, 0);
		} catch (UnsatisfiedLinkError e) {}

		try {
			HWButtonList = new ArrayList<HWButton>(ButtonNumber);
			for (int i = 0; i < ButtonNumber; i++) {
				this.HWButtonList.add(new HWButton(i + 4));
			}
		} catch (UnsatisfiedLinkError e) {}
	}

	public HWButton getHWButton(int index) {
		return this.HWButtonList.get(index);
	}

	@Override
	public void refresh() {
		try {
			Gpio.digitalWrite(STROBE, 0);
			for (int i = 0; i < brailleList.size(); i++) {
				for (int j = brailleList.get(i).getNumberOfPins() - 1; j >= 0; j--) {
					Gpio.digitalWrite(CLOCK, 0);
					if (brailleList.get(i).getPinState(j) == true) {
						Gpio.digitalWrite(DOUT, 1);
					} else {
						Gpio.digitalWrite(DOUT, 0);
					}
					Gpio.delay(1);
					Gpio.digitalWrite(CLOCK, 1);
				}
			}
			Gpio.digitalWrite(STROBE, 1);
		} catch (UnsatisfiedLinkError e) {
		}
	}

	@Override
	public void addSkipButtonListener(int index, String param, ScenarioParser sp) {
		try {
			Gpio.wiringPiISR(getHWButton(index).getGPIOPinNumber(), Gpio.INT_EDGE_FALLING, new GpioInterruptCallback() {
				@Override
				public void callback(int pin) {
					if (sp.userInput) {
						sp.userInput = false;
						if (System.currentTimeMillis() > debounce) {
							debounce = System.currentTimeMillis() + 1000L;
							sp.skip(param);
						}
					}
				}
			});
		} catch (UnsatisfiedLinkError e) {
		} catch (IndexOutOfBoundsException e) {
		}
	}

	@Override
	public void addRepeatButtonListener(int index, ScenarioParser sp) {
		try {
			Gpio.wiringPiISR(getHWButton(index).getGPIOPinNumber(), Gpio.INT_EDGE_RISING, new GpioInterruptCallback() {
				@Override
				public void callback(int pin) {
					if (sp.userInput) {
						if (System.currentTimeMillis() > debounce) {
							debounce = System.currentTimeMillis() + 1000L;
							sp.repeatText();
						}
					}
				}
			});
		} catch (UnsatisfiedLinkError e) {
		} catch (IndexOutOfBoundsException e) {
		}
	}

	@Override
	public void removeButtonListener(int index) {
		try {
			Gpio.wiringPiClearISR(getHWButton(index).getGPIOPinNumber());
		} catch (UnsatisfiedLinkError e) {
		}
	}

}
