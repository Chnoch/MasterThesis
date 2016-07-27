package ch.chnoch.mt.machinelearning.data.preparation

import ch.chnoch.mt.machinelearning.data.model.Model
import ch.chnoch.mt.machinelearning.data.model.ModelEntry
import ch.chnoch.mt.machinelearning.data.model.User

/**
 * Provides helper function to clean up the data. Does the following thing:
 * - Removes Duplicate entries for each user. A duplicate entry is an entry of the same user at the same station
 * with timestamp within the DUPLICATE_TIMEFRAME limitation
 * - Assigns previous and next station to each entry.
 * - Removes Low Profile Users from the model in order to not distort the ML-Algorithms
 */
public class DataCleanup {
    private static final int DUPLICATE_TIMEFRAME = 60
    private static final int LOW_PROFILE_USER_THRESHOLD = 20

    public static Model parseModel(final Model model) {
        List<User> users = model.getUsers()
        println('Users: ' + users.size())
        List<ModelEntry> entries, duplicates
        users.each { user ->
            (entries, duplicates) = removeDuplicates(user.getEntries())

            assignPreviousStations(entries)
            assignNextStations(entries)
            user.setDuplicates(duplicates)
            user.setPreparedEntries(entries)
        }

        removeLowProfileUsers(model)
        return model
    }

    private static void removeLowProfileUsers(final Model model) {
        List<User> users = new ArrayList<>()
        List<User> lowProfileUsers = new ArrayList<>()

        model.getUsers().each { user ->
            if (user.getPreparedEntries().size() < LOW_PROFILE_USER_THRESHOLD) {
                lowProfileUsers.add(user)
            } else {
                users.add(user)
            }
        }
        model.setPreparedUsers(users)
        model.setLowProfileUsers(lowProfileUsers)

        println('prepared users: ' + model.getPreparedUsers().size())
        println('low profile users: ' + model.getLowProfileUsers().size())
    }

    private static void assignPreviousStations(modelEntries) {

        // Just to make sure they're still properly sorted
        modelEntries.sort { a, b ->
            a.timestampStart <=> b.timestampStart
            a.stationId <=> b.stationId
        }

        def previousEntry
        def amountOfNull = 0;

        modelEntries.each() { entry ->
            entry.previousStationId = getStationOrNull(entry, previousEntry)
            entry.previousStationId != null ?: amountOfNull++

            previousEntry = entry
        }
    }


    private static void assignNextStations(List<ModelEntry> modelEntries) {

        // Just to make sure they're still properly sorted
        modelEntries.sort { a, b ->
            a.timestampStart <=> b.timestampStart
            a.stationId <=> b.stationId
        }

        def amountOfNull = 0;
        for (def i = 0; i < modelEntries.size() - 1; i++) {
            def currentEntry = modelEntries[i]
            def nextEntry = modelEntries[i + 1]
//            currentEntry.nextStationId = nextEntry.stationId
            currentEntry.nextStationId = getStationOrNull(currentEntry, nextEntry)
        }
    }

    private static String getStationOrNull(ModelEntry entry, ModelEntry questionableEntry) {
        Date timestamp = entry.timestampStart
        def questionableTimestamp = questionableEntry?.timestampStart

        def hourOfDay = Integer.parseInt(timestamp.format("HH"));
        def calendar = timestamp.toCalendar()
        def day = calendar.get(Calendar.DAY_OF_WEEK)

        def questionableHourOfDay = questionableTimestamp?.format("HH")?.toInteger();
        def questionableCalendar = questionableTimestamp?.toCalendar()
        def questionableDay = questionableCalendar?.get(Calendar.DAY_OF_WEEK)

        if (entry?.userId == questionableEntry?.userId) {
            if (day == questionableDay && !(hourOfDay > 4 && questionableHourOfDay < 4)) {
                return questionableEntry.stationId
            } else if (questionableDay == (day - 1) % 7 && hourOfDay < 4) {
                return questionableEntry.stationId
            }
        }
        return "null"
    }

    private static removeDuplicates(modelEntries) {
        modelEntries.sort { a, b ->
            a.timestampStart <=> b.timestampStart
            a.stationId <=> b.stationId
            a.userId <=> b.userId
        }

        List<ModelEntry> remainingEntries = new ArrayList<>()
        List<ModelEntry> duplicateEntries = new ArrayList<>()

        boolean duplicate = false
        ModelEntry currentItem = null
        modelEntries.eachWithIndex() { value, index ->
            if (currentItem?.userId == value.userId &&
                    currentItem?.stationId == value.stationId &&
                    value.timestampStart - currentItem?.timestampStart < DUPLICATE_TIMEFRAME
            ) {
                duplicate = true
            }

            if (!duplicate) {
                currentItem = value
                remainingEntries.add(currentItem)
            } else {
                duplicateEntries.add(value)
            }

            duplicate = false
        }

        return [remainingEntries, duplicateEntries]
    }
}
