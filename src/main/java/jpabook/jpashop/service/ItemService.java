package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional // 쓰기 트랜잭션 우선
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    // 변경 감지 동작
    @Transactional
    public void updateItem(Long itemId, UpdateItemDto dto) {
        Item item = itemRepository.findOne(itemId); // 영속 상태 Book 얻기
        item.update(dto.getPrice(), dto.getName(), dto.getStockQuantity());
        // 트랜잭션 커밋할 때 update 쿼리 날림
    }


    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }

}
