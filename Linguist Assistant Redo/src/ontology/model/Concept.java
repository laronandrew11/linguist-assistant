package ontology.model;

import grammar.model.Constituent;
import grammar.model.Node;

import java.util.Arrays;
import java.util.List;

import lexicon.model.Entry;
import lexicon.model.EntryDAO;
import commons.dao.DAOFactory;

public class Concept extends Node {
    private Integer pk;
    private String stem;
    private String sense;
    private String gloss;
    private List<Tag> tags;
    private Constituent fConstituent;
    
    private final static List<String> senseList = 
            Arrays.asList("A", "B", "C", "D", "E", "F", "G",
                    "H", "I", "J", "K", "L", "M", "N", "O",
                    "P", "Q", "R", "S", "T", "U", "V", "W",
                    "X", "Y", "Z", "AA", "AB", "AC", "AD");
    
    public static List<Concept> getInstances(String stemSubString, Tag tag, Constituent constituent) {
        DAOFactory factory = DAOFactory.getInstance();
        ConceptDAO dao = new ConceptDAO(factory);
        List<Concept> result;
        if (tag.equals(Tag.getTagAll())) {
            result = dao.retrieveBySubstring(stemSubString, constituent); 
        }
        else {
            result = dao.retrieveByTag(stemSubString, tag, constituent);
        }
        return result;
    }
    
    public static List<Concept> getInstances(String stemSubstring, Constituent constituent) {
        return getInstances(stemSubstring, Tag.getTagAll(), constituent);
    }

    public static Concept getInstance(String stem, String sense, Constituent constituent) {
        DAOFactory factory = DAOFactory.getInstance();
        ConceptDAO dao = new ConceptDAO(factory);
        return dao.retrieve(stem, sense, constituent);
    }
    
    public static Concept getInstance(int pk) {
        DAOFactory factory = DAOFactory.getInstance();
        ConceptDAO dao = new ConceptDAO(factory);
        return dao.retrieve(pk);
    }
    
    
    
    public static Concept getEmpty(Constituent constituent) {
        return new Concept(constituent);
    }
    
    // max of 30 senses only
    public static String getNextSense(String aSense) {
        String sense = senseList.get(0);
        if (aSense != null && !aSense.isEmpty()) {
            int index = senseList.indexOf(aSense);
            sense = senseList.get(index + 1);
        }
        return sense;
    }
    
    public Concept(Constituent aConstituent) {
        this.fConstituent = aConstituent;
        level = aConstituent.getLevel() + 1;
    }
    
    public Concept(String aStem, Constituent aConstituent) {
        this(aConstituent);
        this.stem = aStem;
    }
    
    void setPk(Integer pk) {
        this.pk = pk;
    }
    public Integer getPk() {
        return pk;
    }
    
    public Constituent getParent() {
        return fConstituent;
    }
    
    public void setStem(String aStem) {
        stem = aStem;
    }
    
    public String getStem() {
        return stem;
    }
    
    public void setSense(String sense) {
        this.sense = sense;
    }
    
    public String getSense() {
        return this.sense;
    }
    
    public void setGloss(String aGloss) {
        gloss = aGloss;
    }
    
    public String getGloss() {
        return gloss;
    }
    
    @Override
    public String toString() {
        return this.stem + "-" + this.sense;
    }
    
    public List<Tag> getTags() {
        DAOFactory factory = DAOFactory.getInstance();
        ConceptDAO dao = new ConceptDAO(factory);
        return dao.retrieveAllTags(this);
    }
    
    public List<Entry> getMappings() {
        DAOFactory factory = DAOFactory.getInstance();
        ConceptDAO dao = new ConceptDAO(factory);
        return dao.retrieveMappedEntries(this);
    }
    
    public void addTag(Tag tag) {
        DAOFactory factory = DAOFactory.getInstance();
        ConceptDAO dao = new ConceptDAO(factory);
        dao.addTag(this, tag);
    }
    
    public void deleteTag(Tag tag) {
        DAOFactory factory = DAOFactory.getInstance();
        ConceptDAO dao = new ConceptDAO(factory);
        dao.deleteTag(this, tag);
    }
    
    public void addMapping(Entry entry) {
        DAOFactory factory = DAOFactory.getInstance();
        ConceptDAO dao = new ConceptDAO(factory);
        dao.createMapping(this, entry);
    }
    
    public void deleteMapping(Entry entry) {
        DAOFactory factory = DAOFactory.getInstance();
        ConceptDAO dao = new ConceptDAO(factory);
        dao.deleteMapping(this, entry);
    }
    
    @Override
    public boolean equals(Object other) {
        if (other == null)
            return false;
        if (other == this)
            return true;
        if (!(other instanceof Concept))
            return false;
        
        return toString().equals(other.toString()) ? true : false;
    }
    
    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }
}
