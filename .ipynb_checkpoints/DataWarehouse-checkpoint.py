import json

class Document:                   
    def __init__(self, result) -> None:
        documentId = self.getHash(result)
        self.doc_value = {'DocumentId' : documentId, 'Result' : result}
    def getHash(self, result):
        json_string = json.dumps(result, sort_keys=True)
        hash_value = hash(json_string)     
        return hash_value
    
collection = []