__author__ = 'Chnoch'


class Edge:
    def __init__(self, date, from_node, to_node):
        self.date = date
        self.from_node = from_node
        self.to_node = to_node

    def __lt__(self, other):
        return self.date < other.date

    def __repr__(self):
        return self.date

    def __hash__(self):
        return super.__hash__(self)

    def get_to_node(self):
        return self.to_node

    def get_date(self):
        return self.date