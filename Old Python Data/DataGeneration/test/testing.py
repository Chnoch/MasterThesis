from dateutil.parser import parse
from model.recommendation_engine import RecommendationEngine
from pprint import pprint
from util.json_handling import JSONHandling

__author__ = 'Chnoch'


class Testing:
    def __init__(self, id, model):
        self.id = id
        self.model = model
        self.engine = RecommendationEngine(model)

    def start_test(self):
        data = JSONHandling.import_file('data/' + self.id + '_test.json')
        for record in data:
            station_id = record["stationId"]
            date_time = record["date"] + ' ' + record["time"]
            date = parse(date_time)

            (best_guess, all) = self.engine.get_recommendation(self.id, station_id, date)
            print record["stationName"]
            print date
            print 'best guess: '
            self.print_guess(best_guess)
            print 'all guesses'
            for tuple in all.items():
                self.print_guess(tuple)
            print "That's all"
            print "---"

    def print_guess(self, tuple):
        # print '------------'
        if tuple:
            print 'time: ' + tuple[0].date.strftime("%H:%M:%S")
            print 'to: ' + tuple[0].to_node.__repr__()
            print 'certainty: ' + str(tuple[1]) + '%'
        else:
            print 'No recommendation available'
        print '------------'

