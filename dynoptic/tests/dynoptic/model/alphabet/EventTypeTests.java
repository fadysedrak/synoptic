package dynoptic.model.alphabet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import dynoptic.DynopticTest;
import dynoptic.model.fifosys.channel.channelid.ChannelId;
import dynoptic.model.fifosys.channel.channelid.LocalEventsChannelId;

public class EventTypeTests extends DynopticTest {
    ChannelId cid;
    ChannelId cidCopy;
    ChannelId cid2;
    LocalEventsChannelId localChId;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        cid = new ChannelId(1, 2, 0);
        cidCopy = new ChannelId(1, 2, 0);
        cid2 = new ChannelId(2, 1, 1);
        localChId = new LocalEventsChannelId(2);
    }

    @Test
    public void createLocal() {
        EventType e = EventType.LocalEvent("e", 1);
        assertFalse(e.isCommEvent());
        assertFalse(e.isSendEvent());
        assertFalse(e.isRecvEvent());
        assertFalse(e.isSynthSendEvent());
        assertEquals(e.getEventPid(), 1);
        logger.info(e.toString());
        logger.info(e.toScmTransitionString(localChId));

        assertFalse(e.equals(null));
        assertFalse(e.equals(""));
        assertTrue(e.equals(e));

        // equality:
        EventType e2 = EventType.LocalEvent("e", 1);
        assertEquals(e, e2);

        e2 = EventType.LocalEvent("e", 2);
        assertTrue(!e.equals(e2));

        e2 = EventType.LocalEvent("Z", 1);
        assertTrue(!e.equals(e2));

        e2 = EventType.SendEvent("e", cid);
        assertTrue(!e.equals(e2));

        e2 = EventType.RecvEvent("e", cid);
        assertTrue(!e.equals(e2));
    }

    @Test
    public void createSend() {
        EventType e = EventType.SendEvent("e", cid);
        assertTrue(e.isCommEvent());
        assertTrue(e.isSendEvent());
        assertFalse(e.isRecvEvent());
        assertFalse(e.isSynthSendEvent());
        assertEquals(e.getEventPid(), cid.getSrcPid());
        logger.info(e.toString());
        logger.info(e.toScmTransitionString(localChId));

        assertFalse(e.equals(null));
        assertFalse(e.equals(""));
        assertTrue(e.equals(e));

        // equality:
        EventType e2 = EventType.SendEvent("e", cidCopy);
        assertEquals(e, e2);

        e2 = EventType.SendEvent("Z", cid);
        assertTrue(!e.equals(e2));

        e2 = EventType.SendEvent("e", cid2);
        assertTrue(!e.equals(e2));

        e2 = EventType.RecvEvent("e", cid);
        assertTrue(!e.equals(e2));

        e2 = EventType.LocalEvent("e", 1);
        assertTrue(!e.equals(e2));
    }

    @Test
    public void createRecv() {
        EventType e = EventType.RecvEvent("e", cid);
        assertTrue(e.isCommEvent());
        assertFalse(e.isSendEvent());
        assertTrue(e.isRecvEvent());
        assertFalse(e.isSynthSendEvent());
        assertEquals(e.getEventPid(), cid.getDstPid());
        logger.info(e.toString());
        logger.info(e.toScmTransitionString(localChId));

        assertFalse(e.equals(null));
        assertFalse(e.equals(""));
        assertTrue(e.equals(e));

        // equality:
        EventType e2 = EventType.RecvEvent("e", cidCopy);
        assertEquals(e, e2);

        e2 = EventType.RecvEvent("Z", cid);
        assertTrue(!e.equals(e2));

        e2 = EventType.RecvEvent("e", cid2);
        assertTrue(!e.equals(e2));

        e2 = EventType.SendEvent("e", cid);
        assertTrue(!e.equals(e2));

        e2 = EventType.LocalEvent("e", 1);
        assertTrue(!e.equals(e2));
    }

    @Test
    public void createSynthSend() {
        EventType eToTrace = EventType.RecvEvent("e", cid);

        EventType e = EventType.SynthSendEvent(eToTrace, cid, true);

        assertTrue(e.isCommEvent());
        assertFalse(e.isSendEvent());
        assertFalse(e.isRecvEvent());
        assertTrue(e.isSynthSendEvent());
        assertEquals(e.getEventPid(), cid.getDstPid());
        logger.info(e.toString());
        logger.info(e.toScmTransitionString(localChId));

        assertFalse(e.equals(null));
        assertFalse(e.equals(""));
        assertTrue(e.equals(e));

        // equality:
        EventType e2 = EventType.SynthSendEvent(eToTrace, cid, true);
        assertEquals(e, e2);

        e2 = EventType.SynthSendEvent(eToTrace, cid, false);
        assertTrue(!e.equals(e2));

        e2 = EventType.RecvEvent("e", cid);
        assertTrue(!e.equals(e2));

        e2 = EventType.SendEvent("e", cid2);
        assertTrue(!e.equals(e2));

        e2 = EventType.LocalEvent("e", 1);
        assertTrue(!e.equals(e2));
    }
}