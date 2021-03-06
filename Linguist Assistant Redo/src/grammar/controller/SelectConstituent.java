package grammar.controller;

import grammar.model.Constituent;
import grammar.view.AddConstituentListener;
import grammar.view.AddConstituentPanel;
import grammar.view.GenericDialog;

import java.util.ArrayList;
import java.util.List;

public class SelectConstituent {
    private List<SelectConstituent.Listener> listeners;
    private Constituent destination;
    private int index;
    
    private AddConstituentPanel addConstituentPanel;
    private GenericDialog dialog;
    
    public SelectConstituent(Constituent toAdd, Constituent destination, int index) {
        this.destination = destination;
        this.index = index;
        listeners = new ArrayList<>();
        dialog = new GenericDialog();
        addConstituentPanel = new AddConstituentPanel(toAdd);
        addConstituentPanel.addListener(new AddConstituentListener_());
        dialog.setPanel(addConstituentPanel);
    }
     
    private class AddConstituentListener_ implements AddConstituentListener {
        @Override
        public void clickedOk(Constituent toAdd) {
            destination.moveChild(toAdd, index);
            dialog.closeDialog();
            for (SelectConstituent.Listener listener : listeners) {
                listener.done();
            }
        }

        @Override
        public void clickedCancel() {
            dialog.closeDialog();
            for (SelectConstituent.Listener listener : listeners) {
                listener.done();
            }
        }   
    }
    
    public void addListener(SelectConstituent.Listener listener) {
        listeners.add(listener);
    }
    
    public interface Listener {
        public abstract void done();
    }
}
