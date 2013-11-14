__author__ = 'Chnoch'


from model.edge import Edge
from pprint import pprint
from dateutil.parser import parse
from util.json_handling import JSONHandling


class TrainingData:
    def __init__(self, id):
        self.id = id

    def start_training(self, model):
        data = JSONHandling.import_file('data/' + self.id + '.json')
        prev_node = None
        node = None
        for record in data:
            prev_node = node
            node = model.find_or_create_node(self.id, record["stationId"], record["stationName"])
            model.add_node(node)

            if not prev_node is None:
                date_time = record["date"] + ' ' + record["time"]
                date = parse(date_time)
                edge = Edge(date, node, prev_node)
                model.add_edge(node, prev_node, edge)


