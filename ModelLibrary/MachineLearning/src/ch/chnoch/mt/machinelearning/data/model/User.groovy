package ch.chnoch.mt.machinelearning.data.model

import ch.chnoch.mt.machinelearning.data.model.model_deprecated.Bag
import com.sun.org.apache.xpath.internal.operations.Mod

/**
 * Created by Chnoch on 21.03.2015.
 */
public class User {
    private String userId
    private List<ModelEntry> entries
    private List<ModelEntry> duplicates
    private List<ModelEntry> preparedEntries
    private List<String> availableNextStations


    public User(final String userId) {
        this.userId = userId
        this.entries = new ArrayList<>()
    }

    public void addEntry(final ModelEntry entry) {
        this.entries.add(entry)
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId
    }

    public List<ModelEntry> getEntries() {
        return entries
    }

    public void setEntries(List<ModelEntry> entries) {
        this.entries = entries
    }

    public List<ModelEntry> getPreparedEntries() {
        return preparedEntries
    }

    public void setPreparedEntries(final List<ModelEntry> prepEntries) {
        this.preparedEntries = prepEntries;
    }

    public List<String> getAvailableNextStations() {
        return availableNextStations
    }

    public void setAvailableNextStations(List<String> availableNextStations) {
        this.availableNextStations = availableNextStations
    }

    List<ModelEntry> getDuplicates() {
        return duplicates
    }

    void setDuplicates(List<ModelEntry> duplicates) {
        this.duplicates = duplicates
    }
}
