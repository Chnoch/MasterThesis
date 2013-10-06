import networkx as nx
from numpy.ma.core import _frommethod
import matplotlib.pyplot as plt
from node import Node

__author__ = 'Chnoch'


class Model:

    def __init__(self):
        self.G = nx.Graph()
        self.nodes = list()

    def get_graph(self):
        return self.G

    def add_node(self, node):
        self.G.add_node(node)

    def add_edge(self, from_node, to_node, date):

        if from_node.station_id != to_node.station_id:
            self.G.add_edge(from_node, to_node, date=date)

    def draw_graph(self):
        pos = nx.spring_layout(self.G)
        nx.draw(self.G, pos)
        plt.show()
        plt.savefig("graph.png", dpi=500, facecolor='w', edgecolor='w',orientation='portrait', papertype=None, format=None,transparent=False, bbox_inches=None, pad_inches=0.1)

    def find_or_create_node(self, user_id, station_id, station_name):
        for node in self.nodes:
            if node.station_id == station_id:
                return node

        node = Node(user_id, station_id, station_name)
        self.nodes.append(node)
        return node