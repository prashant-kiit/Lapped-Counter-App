import DataWarehouse as dw
import IngestionClient as ic
import Cleaner as cl

if __name__ == "__main__":
    collection = dw.CollectionSingleton.getCollection()
    collection = ic.start(collection)
    dataframe = cl.start(collection)
    print(dataframe)