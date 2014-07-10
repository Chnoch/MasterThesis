__author__ = 'Chnoch'

import json


class JSONHandling:
    @staticmethod
    def import_file(filename):
        # with open(filename) as data_file:
        #     data = json.load(data_file)
            # pprint(data)
        f = open(filename)
        data = json.load(f)
        f.close()
        return data
