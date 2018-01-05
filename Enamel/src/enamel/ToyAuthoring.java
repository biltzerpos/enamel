package enamel;

public class ToyAuthoring {

    public static void main(String[] args) {
    	Player player = new VisualPlayer();
    	ScenarioParser s = new ScenarioParser(player);
		s.setScenarioFile("FactoryScenarios/Scenario_" + 1 + ".txt");
    }
}