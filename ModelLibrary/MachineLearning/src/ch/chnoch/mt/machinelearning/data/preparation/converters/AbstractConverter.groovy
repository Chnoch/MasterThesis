package ch.chnoch.mt.machinelearning.data.preparation.converters

import ch.chnoch.mt.machinelearning.data.interfaces.IConverter
import ch.chnoch.mt.machinelearning.data.model.Model
import ch.chnoch.mt.machinelearning.data.model.ModelEntry
import org.omg.CORBA.IMP_LIMIT

/**
 * Created by Chnoch on 12.02.2016.
 */
public abstract class AbstractConverter implements IConverter {

    public Model convert(File file) {
        def model = new Model()
        parseFile(file, model)

        return model
    }

    private void parseFile(File file, Model model) {
        file.splitEachLine('\\t') { fields ->
            ModelEntry entry = this.parseLine(fields)
            if (entry.userId) {
                model.addEntry(entry)
            }
        }
    }

    protected abstract ModelEntry parseLine(List<String> fields);
}
