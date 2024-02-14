import pandas as pd

def start(collection):
    dataStage = []
    for document in collection:
        dataStage.append({'DocumentId': document.doc_value['DocumentId'], 'Result': document.doc_value['Result']})

    for index in range(len(dataStage)):
        dataStage[index] = dataStage[index]['Result']
    # print(dataStage)

    dataframe = pd.DataFrame(dataStage)
    return dataframe