from model.model import Model
from training.training_data import TrainingData
from test.testing import Testing

__author__ = 'Chnoch'

def main():
    testing_id = '1'
    data = TrainingData(testing_id)
    model = Model(40)
    data.start_training(model)
    # model.draw_graph()

    test = Testing(testing_id, model)
    test.start_test()


main()