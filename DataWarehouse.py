import json

def getHash(result):
    json_string = json.dumps(result, sort_keys=True)
    hash_value = hash(json_string)     
    return hash_value

class RawDocument:                   
    def __init__(self, result):
        documentId = getHash(result)
        self.doc_value = {'DocumentId' : documentId, 'Result' : result}

class DataframeDocument:
    def __init__(self, dataframe):
        documentId = getHash(dataframe)
        self.doc_value = {'DocumentId' : documentId, 'Dataframe' : dataframe}

class CollectionSingleton:
    collectionRawDocument = None
    collectionDataframe = None

    @classmethod
    def __new__(cls):
        pass

    @classmethod
    def getCollectionRawDocument(cls):
        if cls.collectionRawDocument is None:
            cls.collectionRawDocument = []
        return cls.collectionRawDocument
    
    @classmethod
    def getCollectionDataframe(cls):
        if cls.collectionDataframe is None:
            cls.collectionDataframe = []
        return cls.collectionDataframe