package mobi.boilr.libpricealarm;

import java.io.IOException;

import mobi.boilr.libdynticker.core.Exchange;
import mobi.boilr.libdynticker.core.Pair;

public class PriceChangeAlarm extends Alarm {

	private static final long serialVersionUID = -5424769817492896869L;
	private double change;
	private float percent;

	public PriceChangeAlarm(int id, Exchange exchange, Pair pair, long period, Notify notify,
			double change) throws NumberFormatException, IOException {
		super(id, exchange, pair, period, notify);
		this.change = change;
		percent = 0;
		lastValue = getExchangeLastValue();
	}

	public PriceChangeAlarm(int id, Exchange exchange, Pair pair, long period, Notify notify,
			float percent) throws NumberFormatException, IOException {
		super(id, exchange, pair, period, notify);
		this.percent = percent;
		lastValue = getExchangeLastValue();
		change = lastValue * (percent * 0.01);
	}

	@Override
	public boolean run() throws NumberFormatException, IOException {
		boolean ret = true;
		double newValue = getExchangeLastValue();
		if(Math.abs(lastValue - newValue) >= change) {
			ret = notify.trigger(getId());
		}
		lastValue = newValue;
		if(percent > 0) {
			change = lastValue * (percent * 0.01);
		}
		return ret;
	}

	public double getChange() {
		return change;
	}

	public void setChange(double variation) {
		this.change = variation;
	}

	public float getPercent() {
		return percent;
	}

	public void setPercent(float percent) {
		this.percent = percent;
	}

	public boolean isPercent() {
		return percent != 0;
	}
}