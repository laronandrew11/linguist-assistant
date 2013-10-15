package controller;

import model.Constituent;
import model.Feature;
import view.AddConstituentListener;
import view.AddConstituentPanel;
import view.BlockListener;
import view.FeatureValuesListener;
import view.GenericDialog;
import view.MainFrame;
import view.SemanticEditorPanel;

public class SemanticEditor {
    private SemanticEditorPanel display;
    
    public static void main(String[] args) {
        new SemanticEditor();
    }
    
    public SemanticEditor() {
        MainFrame frame = new MainFrame();
        display = new SemanticEditorPanel();
        display.addBlockListener(new ImpBlockListener());
        display.addFeatureValuesListener(new ImpFeatureValuesListener());
        frame.setPanel(display);
        
        Constituent con = new Constituent("C", null);
        Constituent con2 = new Constituent("N", con);
        Constituent con3 = new Constituent("V", con);
        Constituent con4 = new Constituent("R", con);
        Constituent con5 = new Constituent("E", con);
        
        con.addChild(con2);
        con.addChild(con3);
        con.addChild(con4);
        con.addChild(con5);
        display.updateConstituent(con);
    }
    
    private class ImpBlockListener implements BlockListener {
        @Override
        public void selectedConstituent(Constituent constituent) {}

        @Override
        public void droppedBlock(Constituent dropped, Constituent destination, int index) {
            destination.moveChild(dropped, index);
            display.refresh();
        }

        @Override
        public void droppedButton(Constituent dropped, Constituent destination, int index) {
            AddConstituent addConstituent = new AddConstituent(dropped, destination, index);
            addConstituent.addListener(new AddConstituentListener());
        }
    }
    
    private class ImpFeatureValuesListener implements FeatureValuesListener {
        @Override
        public void featureValueChanged(Feature feature, String newValue) {
            Constituent parent = feature.getParent();
            parent.updateFeature(feature, newValue);
            display.refresh();
        }
    }
    
    private class AddConstituentListener implements AddConstituent.Listener {
        @Override
        public void done() {
            display.refresh();
        }
    }

}