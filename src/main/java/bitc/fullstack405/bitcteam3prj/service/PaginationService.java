package bitc.fullstack405.bitcteam3prj.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.IntStream;

@Service
public class PaginationService {

    private static final int PAGINATION_BAR_LENGTH = 5;

    public List<Integer> getPaginationBarNumbers(int currentPageNumber, int totalPages){
        int startNumber = Math.max(0,currentPageNumber - (PAGINATION_BAR_LENGTH  / 2));
        int endNumber = Math.min(startNumber + PAGINATION_BAR_LENGTH, totalPages);

        return IntStream.range(startNumber, endNumber).boxed().toList();
    }

    public int currentBarLength(){
        return PAGINATION_BAR_LENGTH;
    }
}
