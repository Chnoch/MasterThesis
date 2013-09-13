import networkx as nx
from numpy.ma.core import _frommethod

__author__ = 'Chnoch'


class Model:

    def __init__(self):
        self.G = nx.Graph()

    def get_graph(self):
        return self.G

    def add_node(self, node):
        self.G.add_node(node)

    def add_edge(self, from_node, to_node, date):
        self.G.add_edge(from_node, to_node, date=date)
