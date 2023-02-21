package notely.app;

public class NoteCard{
    private String term;
    private String definition;
    private int priorityNum;

    public NoteCard(){
        term = "";
        definition = "";
        priorityNum = 0;
    }
    public NoteCard(String term, String definition, int priorityNum){
        this.term = term;
        this.definition = definition;
        this.priorityNum = priorityNum;
    }
    public void setTerm(String term){
        this.term = term;
    }
    public void setDefinition(String definition){
        this.definition = definition;
    }
    public void setPriorityNum(int priorityNum){
        this.priorityNum = priorityNum;
    }
    public String getTerm(){
        return term;
    }
    public String getDefinition(){
        return definition;
    }
    public int getPriorityNum(){
        return priorityNum;
    }

}
