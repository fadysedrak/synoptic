package csight.mc.mcscm;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import csight.CSightTest;
import csight.invariants.BinaryInvariant;
import csight.mc.MCResult;
import csight.model.fifosys.cfsm.CFSM;
import csight.model.fifosys.gfsm.GFSM;
import csight.util.Util;

public class McScMRunnerTests extends CSightTest {
    // TODO: implement tests for running in parallel
    private McScMRunner mcRunner;
    private McScM mcscm;
    private String verifyPath;
    private GFSM pGraph;
    private List<BinaryInvariant> invariants;
    private int timeOut = 60;
    
    @Before
    public void setUp() throws Exception {
        super.setUp();

        // NOTE: hard-coded assumption about where the tests are run
        verifyPath = CSightTest.getMcPath();
        mcscm = new McScM(verifyPath);
        
        // TODO: set up GFSM here for testing
    }
    
    @Test
    public void testRunOneWithOneInv() throws Exception {
        List<BinaryInvariant> invs = Util.newList();
        invs.add(invariants.get(0));
        
        mcRunner = new McScMRunner(verifyPath, 1);
        mcRunner.verify(pGraph, invs, timeOut, false);
        
        //TODO
        // assertRunnerResult
    }
    
    @Test
    public void testRunOneWithManyInvs() throws Exception {
        // TODO
    }
    
    @Test
    public void testRunManyWithOneInvs() throws Exception {
        // TODO
    }
    
    @Test
    public void testRunManyWithEqualInvs() throws Exception {
        // TODO
    }
    
    @Test
    public void testRunManyWithManyInvs() throws Exception {
        // TODO
    }
    
    private void assertRunnerResult(GFSM gfsm, BinaryInvariant inv,
            boolean minimize, MCResult runnerResult) throws Exception {
        CFSM cfsm = gfsm.getCFSM(minimize);
        cfsm.augmentWithInvTracing(inv);

        String mcInputStr = cfsm.toScmString("checking_scm_"
                + inv.getConnectorString());
        
        mcscm.verify(mcInputStr, timeOut);
        MCResult result = mcscm.getVerifyResult(cfsm.getChannelIds());
        
        assertEquals(result.toRawString(), runnerResult.toRawString());
    }
}