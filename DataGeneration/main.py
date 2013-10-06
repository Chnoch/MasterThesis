from model.model import Model
from training.training_data import TrainingData

__author__ = 'Chnoch'

def main():
    data = TrainingData('1.1')
    model = Model()
    data.start_training(model)
    model.draw_graph()


main()