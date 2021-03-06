package ch.chnoch.mt.machinelearning.data.evaluation

import ch.chnoch.mt.machinelearning.data.evaluation.classifiers.DecisionTreeClassifier
import weka.gui.treevisualizer.PlaceNode2
import weka.gui.treevisualizer.TreeVisualizer

import java.awt.BorderLayout

/**
 * Created by Chnoch on 02.12.2015.
 */
class ModelEvaluation {

    private model;

    public ModelEvaluation(model) {
        this.model = model
    }

    public evaluateModelForUser() {

        def correct = 0;
        def incorrect = 0;
        def count = 0;
        def users = model.getPreparedUsers()
        users.each { user ->
            def instances = ModelToWekaDataConverter.getInstancesForUser(model, user)

            if (instances?.size() > 20) {
                instances.setClassIndex(instances.numAttributes() - 1)
                def decisionTree = new DecisionTreeClassifier(instances)
                def classifier = decisionTree.classifyModel()
                def evaluation = decisionTree.evaluateModel(classifier)
                correct += evaluation.correct()
                incorrect += evaluation.incorrect()
                count++;
                System.println('Correct: ' + evaluation.correct())
                System.println('Incorrect: ' + evaluation.incorrect())
                System.println('Correctly Classified: ' + evaluation.correct() / (evaluation.correct() + evaluation.incorrect()))
                System.println('Incorrectly Classified: ' + evaluation.incorrect() / (evaluation.correct() + evaluation.incorrect()))
                System.println('--------------------------------------')
            }
        }

        System.println('Complete Count: ' + count)
        System.println('Correct: ' + correct)
        System.println('Incorrect: ' + incorrect)
        System.println('Correctly Classified: ' + correct / (correct + incorrect))
        System.println('Incorrectly Classified: ' + incorrect / (correct + incorrect))
    }

    public visualizeTree(cls) {
        // display classifier
        final javax.swing.JFrame jf =
                new javax.swing.JFrame("Weka Classifier Tree Visualizer: J48");
        jf.setSize(2000,1000);
        jf.getContentPane().setLayout(new BorderLayout());
        TreeVisualizer tv = new TreeVisualizer(null,
                cls.graph(),
                new PlaceNode2());
        jf.getContentPane().add(tv, BorderLayout.CENTER);
        jf.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                jf.dispose();
            }
        });

        jf.setVisible(true);
        tv.fitToScreen();
    }
}
