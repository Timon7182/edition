package kz.danik.edition.app;

import kz.danik.edition.dto.DictionaryDto;
import kz.danik.edition.entity.AbstractDictionary;
import org.springframework.stereotype.Component;

@Component("edt_CommonMapperService")
public class CommonMapperService {

    public DictionaryDto mapDicToDto(AbstractDictionary dictionary) {

        if(dictionary == null)
            return null;

        DictionaryDto dto = new DictionaryDto();

        dto.setId(dictionary.getId());
        dto.setLangValue(dictionary.getLangValueRu());
        dto.setCode(dictionary.getCode());
        return dto;
    }
}