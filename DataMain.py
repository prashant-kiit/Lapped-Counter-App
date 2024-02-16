import DataWarehouse
import IngestionClient
import Cleaner

if __name__ == "__main__":
    # get collection instances
    collectionRawDocument = DataWarehouse.CollectionSingleton.getCollectionRawDocument()
    collectionDataframe = DataWarehouse.CollectionSingleton.getCollectionDataframe()
    # ingestion of ram data from database to data warehouse
    collectionRawDocument = IngestionClient.start(collectionRawDocument)
    # cleaning of raw data in data warehouse to form dataframes
    collectionDataframe = Cleaner.start(collectionRawDocument, collectionDataframe)
    # scheduling
    # caching
    # load only the update records
    # remove extra socket handshake in QueryClient
    # etl to build dataframes that will used from report creation

    print(collectionDataframe)