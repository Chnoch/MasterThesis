__author__ = 'Chnoch'

import datetime
from collections import OrderedDict
from dateutil.parser import parse


class RecommendationEngine:
    # Time threshold is currently 1 hour. Possibly important to modify that dynamically based on how
    # dense the appointments are
    time_threshold = 1 * 3600

    def __init__(self, model):
        self.model = model

    def get_recommendation(self, user_id, station_id, current_date):
        possible_connections = {}
        nodes = self.model.get_edges_by_nodes_for_station(station_id)
        for nodePair in nodes.items():

        for edgeDict in edges:
            edge = edgeDict['edge']
            date = edge.date
            likeliness = self.compare_dates(current_date, date)
            if likeliness > -1:
                possible_connections[edge] = likeliness

        ordered_connections = OrderedDict(sorted(possible_connections.items(), key=lambda t: t[1]))
        if 0 < ordered_connections.__len__():
            best_guess = ordered_connections.items()[0]
            return best_guess, ordered_connections
        else:
            return None, None

    def compare_dates(self, current_date, date):
        delta = datetime.timedelta(days=0, hours=date.hour - current_date.hour,
                                   minutes=date.minute - current_date.minute, seconds=date.second - current_date.second)

        likeliness = 0

        if current_date.weekday == date.weekday:
            likeliness +=

        # print 'delta: ' + delta.__str__()
        if 0 < delta.seconds < RecommendationEngine.time_threshold:
            return 100 if delta.seconds == 0 else float(delta.seconds) / float(RecommendationEngine.time_threshold) * 100
        else:
            return -1


    def get_edges_with_same_station(self, edges):
        OrderedDict()