package mobi.boilr.libpricealarm;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Timer;

import mobi.boilr.libdynticker.core.Exchange;
import mobi.boilr.libdynticker.core.Pair;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PriceHitAlarmTest {

	private PriceHitAlarm testAlarm;
	private TimerTaskAlarmWrapper wrapper;
	private Notify notify;
	private Exchange exchange;
	private Pair pair;
	private static final int alarmID = 1;

	@Before
	public void setUp() throws Exception {
		notify = mock(Notify.class);
		exchange = mock(Exchange.class);
		pair = mock(Pair.class);
		Timer timer = new Timer();
		testAlarm = new PriceHitAlarm(alarmID, exchange, pair, 1000, notify, 0.0043, 0.0042);
		wrapper = new TimerTaskAlarmWrapper(testAlarm, timer);
	}

	@After
	public void tearDown() throws Exception {
		wrapper.turnOff();
	}

	@Test
	public void testUpperBoundNoReset() throws IOException {
		when(notify.trigger(alarmID)).thenReturn(false);
		when(exchange.getLastValue(pair)).thenReturn(0.00445625);
		verify(notify, timeout(1500).times(1)).trigger(alarmID);
	}

	@Test
	public void testUpperBoundAndReset() throws IOException {
		when(notify.trigger(alarmID)).thenReturn(true);
		when(exchange.getLastValue(pair)).thenReturn(0.00445625);
		verify(notify, timeout(2500).times(2)).trigger(alarmID);
	}

	@Test
	public void testLowerBoundNoReset() throws IOException {
		when(notify.trigger(alarmID)).thenReturn(false);
		when(exchange.getLastValue(pair)).thenReturn(0.004123);
		verify(notify, timeout(1500).times(1)).trigger(alarmID);
	}

	@Test
	public void testNoBoundHit() throws IOException {
		when(notify.trigger(alarmID)).thenReturn(false);
		when(exchange.getLastValue(pair)).thenReturn(0.0042523);
		verify(notify, timeout(1500).never()).trigger(alarmID);
	}

	@Test
	public void testToString() {
		when(pair.getCoin()).thenReturn("XXX");
		when(pair.getExchange()).thenReturn("YYY");
		when(exchange.getName()).thenReturn("DummyExchange");
		Assert.assertEquals("PriceHitAlarm XXX YYY DummyExchange", testAlarm.toString());
	}
}
