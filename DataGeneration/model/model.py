import networkx as nx
import matplotlib.pyplot as plt
from node import Node

__author__ = 'Chnoch'


class Model:

    def __init__(self, max_weight):
        self.G = nx.MultiDiGraph()
        self.nodes = list()
        self.max_weight = max_weight

    def get_graph(self):
        return self.G

    def add_node(self, node):
        self.G.add_node(node)

    def add_edge(self, from_node, to_node, edge):
        if from_node.station_id == to_node.station_id:
            return

        if self.G.number_of_edges() >= self.max_weight:
            self.remove_oldest_edge()

        self.G.add_edge(to_node, from_node, edge=edge)

    def remove_oldest_edge(self):
        edge_to_remove = self.G.edges(data=True)[-1]
        self.G.remove_edge(edge_to_remove[0], edge_to_remove[1], edge_to_remove[2]['edge'].date)

    def draw_graph(self):
        pos = nx.spring_layout(self.G)
        nx.draw(self.G, pos)
        plt.show()
        plt.savefig("graph.png", dpi=500, facecolor='w', edgecolor='w',orientation='portrait', papertype=None, format=None,transparent=False, bbox_inches=None, pad_inches=0.1)

    def find_or_create_node(self, user_id, station_id, station_name):
        node = self.find_node(station_id)
        if node is None:
            node = Node(user_id, station_id, station_name)
        self.nodes.append(node)
        return node

    def find_node(self, station_id):
        for node in self.nodes:
            if node.station_id == station_id:
                return node

    def get_edges_for_station(self, station_id):
        node = self.find_node(station_id)
        edges = (edge[2] for edge in self.G.edges(node, data=True))
        return edges



