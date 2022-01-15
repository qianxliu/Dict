package com.qianxin.dict.util;

import com.hankcs.hanlp.mining.word2vec.DocVectorModel;

import java.util.List;
import java.util.Map;

public class Similar {
    /*
    public DocVectorModel initDocModel(String modelFilePath) throws IOException {
        return new DocVectorModel(new WordVectorModel(modelFilePath));
    }
     */
    DocVectorModel docVectorModel;

    public Similar(DocVectorModel doc, List<String> documents) {
        docVectorModel = doc;
        for (int i = 0; i < documents.size(); i++) {
            docVectorModel.addDocument(i, documents.get(i));
        }
    }

    public List<Map.Entry<Integer, Float>> docNearest(String document, int n) {
        /*
        for (Map.Entry<Integer, Float> entry : entryList)
        {
            System.out.printf("%d %s %.2f\n", entry.getKey(), documents[entry.getKey()], entry.getValue());
        }
         */
        return docVectorModel.nearest(document, n);
    }

}
