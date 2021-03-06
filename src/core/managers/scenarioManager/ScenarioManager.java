package core.managers.scenarioManager;

import constants.CONFIG;
import core.managers.instanceManager.InstanceManager;
import core.managers.GameManager;
import core.managers.factory.ScenarioFactory;
import core.managers.scenarioManager.scenarios.Scenario;
import core.objects.PowerUpRate;
import core.objects.configObject.ScenarioConfig;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class ScenarioManager {

    private final GameManager gameManager;
    private final InstanceManager instanceManager;
    private final ScenarioDifficultyManager difficultyManager;


    private final ScheduledExecutorService scheduler;

    /**
     * Current scenario task running
     */
    protected TimedScenarioTask scenarioTask;

    /**
     * Current list of SCENARIOS
     */
    protected ArrayList<ScenarioConfig> scenarioConfigs;

    /**
     * Range of SCENARIOS invocation
     */
    protected long minInterval;
    protected long maxInterval;

    /**
     * Current difficulty of the scenario
     */
    protected int difficulty;

    /**
     * PowerUp object
     */
    protected PowerUpRate powerup;

    /**
     * Number of current iteration (number of scenario invocated)
     */
    private int iteration;

    public ScenarioManager(GameManager gameManager) {

        this.gameManager = gameManager;
        this.instanceManager = gameManager.getInstanceManager();
        this.difficultyManager = new ScenarioDifficultyManager(this);

        this.scenarioConfigs = gameManager.getScenarioConfigs().stream()
                .filter(s -> s.getDifficulty() < 1)
                .collect(Collectors.toCollection(ArrayList::new));

        // DEFAULT VALUES
        this.difficulty = 0;
        this.minInterval = CONFIG.START_MIN_INTERVAL;
        this.maxInterval = CONFIG.MAX_INTERVAL;
        // default multiplier
        this.powerup = new PowerUpRate(1, 1, 1);

        // Scheduler
        this.scheduler = Executors.newScheduledThreadPool(0);
        this.scenarioTask = new TimedScenarioTask(this::scheduledTask, this.minInterval, this.maxInterval, TimeUnit.MILLISECONDS, scheduler);
    }

    protected GameManager getGameManager() {
        return gameManager;
    }

    /**
     * Start the engine (time thread)
     */
    public void start() {
        this.iteration = 0;
        this.scenarioTask.start();
        System.out.println("start call scenario manager");
    }

    public void pause() {
        this.scenarioTask.pause();
        System.out.println("pause call scenario manager");
    }

    public void resume() {
        this.scenarioTask.resume();
        System.out.println("resume call scenario manager");
    }

    public void reset() {
        this.scheduler.shutdownNow();
        System.out.println("reset call scenario manager");
    }

    /**
     * Get a random scenario config and instantiate the scenario
     * @return Scenario
     */
    private Scenario getNextScenario() {
        if (this.iteration == 1) {
            this.minInterval = CONFIG.MIN_INTERVAL;
            this.scenarioTask.updateIntervals(this.minInterval, this.maxInterval);
        }

        this.iteration += 1;

        this.difficultyManager.increaseDifficulty(this.iteration);

        // pick randomly a scenario within all current scenario config (various with difficulty)
        int index = new Random().nextInt(this.scenarioConfigs.size());
        return ScenarioFactory.createOneScenario(this.scenarioConfigs.get(index), this.powerup);
    }

    private boolean scheduledTask() {
        Scenario next = this.getNextScenario();
        this.gameManager.increasePointByIter(next.getDifficulty());
        if(next == null) {
            return false;
        }

        next.start(this.instanceManager);
        return true;
    }
}
