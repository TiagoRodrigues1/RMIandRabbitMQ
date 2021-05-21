package edu.ufp.inf.sd.project.client;

import edu.ufp.inf.sd.project.server.JobGroupRI;
import edu.ufp.inf.sd.project.server.SchedulingState;
import edu.ufp.inf.sd.project.util.tabusearch.TabuSearchJSSP;

import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WorkerImpl implements WorkerRI {
    private int id;
    private int credits;
    private JobGroupRI jobGroupRI;
    private SchedulingState lastWorkerState;

    @Override
    public SchedulingState getLastWorkerState() {
        return lastWorkerState;
    }
    @Override
    public void setLastWorkerState(SchedulingState lastWorkerState) {
        this.lastWorkerState = lastWorkerState;
    }

    public WorkerImpl(int id) {
        this.id = id;
        this.credits = 0;
    }

    @Override
    public void setJobGroup(JobGroupRI jobGroup) {
        this.jobGroupRI = jobGroup;
    }

    @Override
    public JobGroupRI getJobGroup() {
        return this.jobGroupRI;
    }

    @Override
    public int getId(){
        return this.id;
    }
    @Override
    public String toString() {
        return "Worker{" +
                "id=" + id +
                ", credits=" + credits +
                ",jobgroup " + jobGroupRI.getId() +
                '}';
    }
    @Override
    public int runTS(String jsspInstance) throws RemoteException {
        TabuSearchJSSP ts = new TabuSearchJSSP(jsspInstance);
        int makespan = ts.run();
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, this.id + " [TS] Makespan for {0} = {1}", new Object[]{jsspInstance,String.valueOf(makespan)});
        
        return makespan;
    }
    @Override
    public void update() throws RemoteException {
        try {
            lastWorkerState = this.jobGroupRI.getSchedulingState();
            int makespan = runTS(lastWorkerState.getInstace());
            lastWorkerState.setMakespan(makespan);
            notifyJobGroup();
        }catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void notifyJobGroup() throws RemoteException {
        this.jobGroupRI.update(this.id);
    }

    public void addCredits(int credits) {
        if(credits > 0) {
            this.credits += credits;
        }
    }
}
