package mobi.boilr.libpricealarm;

import static org.mockito.Mockito.mock;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Timer;

import mobi.boilr.libdynticker.core.Pair;
import mobi.boilr.libdynticker.exchanges.BitstampExchange;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SerializationTest {

	private Notify notify;
	private static long exchangeExpiredPeriod = 604800000L; // One week
	private Alarm testAlarm;
	private Alarm newAlarm;

	@Before
	public void setUp() throws Exception {
		notify = mock(Notify.class);

	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testPriceHitAlarmSerialization() throws IOException, ClassNotFoundException,
			UpperLimitSmallerOrEqualLowerLimitException {

		testAlarm = new PriceHitAlarm(1, new BitstampExchange(exchangeExpiredPeriod), new Pair("BTC", "USD"), 1000, notify, 600, 580);
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		ObjectOutputStream objectOutputStream = null;
		objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
		objectOutputStream.writeObject(testAlarm);
		byte[] bytes = byteArrayOutputStream.toByteArray();
		objectOutputStream.close();
		byteArrayOutputStream.close();
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
		ObjectInputStream in = null;
		in = new ObjectInputStream(byteArrayInputStream);
		newAlarm = (PriceHitAlarm) in.readObject();
		in.close();
		byteArrayInputStream.close();
		Assert.assertTrue(newAlarm instanceof PriceHitAlarm);
		Assert.assertEquals(((PriceHitAlarm) testAlarm).getLowerLimit(), ((PriceHitAlarm) newAlarm).getLowerLimit(), 0.1);
		Assert.assertEquals(((PriceHitAlarm) testAlarm).getUpperLimit(), ((PriceHitAlarm) newAlarm).getUpperLimit(), 0.1);
		Assert.assertNotNull(newAlarm);
		Assert.assertEquals(testAlarm.getClass(), newAlarm.getClass());
		Assert.assertEquals(testAlarm.getPair(), newAlarm.getPair());
		Assert.assertEquals(testAlarm.getPeriod(), newAlarm.getPeriod());
		Assert.assertEquals(testAlarm.getExchangeCode(), newAlarm.getExchangeCode());
		Assert.assertNotNull(testAlarm.getNotify());
		Assert.assertNull(newAlarm.getExchange());

	}

	@Test
	public void testPriceHitLowerLimitAlarmSerialization() throws IOException,
			ClassNotFoundException, UpperLimitSmallerOrEqualLowerLimitException {
		testAlarm = new PriceHitLowerLimitAlarm(1, new BitstampExchange(exchangeExpiredPeriod), new Pair("BTC", "USD"), 1000, notify, 0.001);
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		ObjectOutputStream objectOutputStream = null;
		objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
		objectOutputStream.writeObject(testAlarm);
		byte[] bytes = byteArrayOutputStream.toByteArray();
		objectOutputStream.close();
		byteArrayOutputStream.close();
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
		ObjectInputStream in = null;
		in = new ObjectInputStream(byteArrayInputStream);
		newAlarm = (PriceHitLowerLimitAlarm) in.readObject();
		in.close();
		byteArrayInputStream.close();
		Assert.assertTrue(newAlarm instanceof PriceHitLowerLimitAlarm);
		Assert.assertEquals(((PriceHitAlarm) testAlarm).getLowerLimit(), ((PriceHitAlarm) newAlarm).getLowerLimit(), 0.1);
		Assert.assertEquals(((PriceHitAlarm) testAlarm).getUpperLimit(), ((PriceHitAlarm) newAlarm).getUpperLimit(), 0.1);

		Assert.assertEquals(testAlarm.getClass(), newAlarm.getClass());
		Assert.assertEquals(testAlarm.getPair(), newAlarm.getPair());
		Assert.assertEquals(testAlarm.getPeriod(), newAlarm.getPeriod());
		Assert.assertEquals(testAlarm.getExchangeCode(), newAlarm.getExchangeCode());
		Assert.assertNotNull(testAlarm.getNotify());
		Assert.assertNull(newAlarm.getExchange());
	}

	@Test
	public void testPriceHitUpperLimitAlarmSerialization() throws IOException,
			ClassNotFoundException, UpperLimitSmallerOrEqualLowerLimitException {
		testAlarm = new PriceHitUpperLimitAlarm(1, new BitstampExchange(exchangeExpiredPeriod), new Pair("BTC", "USD"), 1000, notify, 0.001);
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		ObjectOutputStream objectOutputStream = null;
		objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
		objectOutputStream.writeObject(testAlarm);
		byte[] bytes = byteArrayOutputStream.toByteArray();
		objectOutputStream.close();
		byteArrayOutputStream.close();
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
		ObjectInputStream in = null;
		in = new ObjectInputStream(byteArrayInputStream);
		newAlarm = (PriceHitUpperLimitAlarm) in.readObject();
		in.close();
		byteArrayInputStream.close();

		Assert.assertNotNull(newAlarm);
		Assert.assertTrue(newAlarm instanceof PriceHitUpperLimitAlarm);
		Assert.assertEquals(((PriceHitAlarm) testAlarm).getLowerLimit(), ((PriceHitAlarm) newAlarm).getLowerLimit(), 0.1);
		Assert.assertEquals(((PriceHitAlarm) testAlarm).getUpperLimit(), ((PriceHitAlarm) newAlarm).getUpperLimit(), 0.1);

		Assert.assertEquals(testAlarm.getClass(), newAlarm.getClass());
		Assert.assertEquals(testAlarm.getPair(), newAlarm.getPair());
		Assert.assertEquals(testAlarm.getPeriod(), newAlarm.getPeriod());
		Assert.assertEquals(testAlarm.getExchangeCode(), newAlarm.getExchangeCode());
		Assert.assertNotNull(testAlarm.getNotify());
		Assert.assertNull(newAlarm.getExchange());
	}

	@Test
	public void testPriceChangeAlarmSerialization() throws IOException, ClassNotFoundException {
		testAlarm = new PriceChangeAlarm(1, new BitstampExchange(exchangeExpiredPeriod), new Pair("BTC", "USD"), 1000, notify, 50);
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		ObjectOutputStream objectOutputStream = null;
		objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
		objectOutputStream.writeObject(testAlarm);
		byte[] bytes = byteArrayOutputStream.toByteArray();
		objectOutputStream.close();
		byteArrayOutputStream.close();
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
		ObjectInputStream in = null;
		in = new ObjectInputStream(byteArrayInputStream);
		newAlarm = (PriceChangeAlarm) in.readObject();
		in.close();
		byteArrayInputStream.close();
		Assert.assertNotNull(newAlarm);
		Assert.assertTrue(newAlarm instanceof PriceChangeAlarm);
		Assert.assertEquals(((PriceChangeAlarm) testAlarm).getPercent(), ((PriceChangeAlarm) newAlarm).getPercent(), 0);
		Assert.assertEquals(((PriceChangeAlarm) testAlarm).getChange(), ((PriceChangeAlarm) newAlarm).getChange(), 0);

		Assert.assertEquals(testAlarm.getClass(), newAlarm.getClass());
		Assert.assertEquals(testAlarm.getPair(), newAlarm.getPair());
		Assert.assertEquals(testAlarm.getPeriod(), newAlarm.getPeriod());
		Assert.assertEquals(testAlarm.getExchangeCode(), newAlarm.getExchangeCode());
		Assert.assertNotNull(testAlarm.getNotify());
		Assert.assertNull(newAlarm.getExchange());
	}

	@Test
	public void testTimerTaskAlarmWrapperSerialization() throws IOException, ClassNotFoundException {
		PriceChangeAlarm testAlarm = new PriceChangeAlarm(1, new BitstampExchange(exchangeExpiredPeriod), new Pair("BTC", "USD"), 1000, notify, 50);
		TimerTaskAlarmWrapper wrapper = new TimerTaskAlarmWrapper(testAlarm, new Timer());
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		ObjectOutputStream objectOutputStream = null;
		objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
		objectOutputStream.writeObject(wrapper);
		byte[] bytes = byteArrayOutputStream.toByteArray();
		objectOutputStream.close();
		byteArrayOutputStream.close();
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
		ObjectInputStream in = null;
		in = new ObjectInputStream(byteArrayInputStream);
		TimerTaskAlarmWrapper newWrapper = null;
		newWrapper = (TimerTaskAlarmWrapper) in.readObject();
		in.close();
		byteArrayInputStream.close();
		Assert.assertEquals(wrapper.getClass(), newWrapper.getClass());
		Assert.assertEquals(wrapper.getAlarm().getClass(), testAlarm.getClass());
		Assert.assertNull(newWrapper.getTimer());
	}
}
