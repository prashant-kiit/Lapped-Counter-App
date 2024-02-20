import pandas as pd

def start(collectionRawDocument, collectionDataframe):
    dataStage = []
    for rawDocument in collectionRawDocument:
        dataStage.append(rawDocument.doc_value['Result'])

    # print(dataStage)

    dataframe = pd.DataFrame(dataStage)
    collectionDataframe.append(dataframe)
    return collectionDataframe