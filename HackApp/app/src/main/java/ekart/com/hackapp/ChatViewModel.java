package ekart.com.hackapp;

import ekart.com.hackapp.models.ItemDetail;
import io.reactivex.subjects.PublishSubject;
import lombok.Getter;

/**
 * Created by brinder.singh on 23/06/17.
 */
@Getter
public enum ChatViewModel {
    INSTANCE;
    private final PublishSubject<ItemDetail> itemDetailPublishSubject = PublishSubject.create();
    private final PublishSubject<Boolean> closeDialogPublishSubject = PublishSubject.create();

    public void publishItemDetailSelection(ItemDetail itemDetail) {
        itemDetailPublishSubject.onNext(itemDetail);
    }

    public void publishCloseDialogPublishSubject(Boolean b) {
        closeDialogPublishSubject.onNext(b);
    }
}
