import json

class Document:                   
    def __init__(self, result) -> None:
        documentId = self.getHash(result)
        self.doc_value = {'DocumentId' : documentId, 'Result' : result}

    def getHash(self, result):
        json_string = json.dumps(result, sort_keys=True)
        hash_value = hash(json_string)     
        return hash_value

class CollectionSingleton:
    collection = None

    @classmethod
    def __new__(cls):
        pass

    @classmethod
    def getCollection(cls):
        if cls.collection is None:
            cls.collection = []
        return cls.collection