__author__ = 'Chnoch'


class Edge:
    def __init__(self, date):
        self.date = date

    def __lt__(self, other):
        return self.date < other.date


