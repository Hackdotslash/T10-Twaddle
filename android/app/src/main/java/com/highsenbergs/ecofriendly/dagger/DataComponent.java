package com.highsenbergs.ecofriendly.dagger;
import com.highsenbergs.ecofriendly.ui.activities.MainActivity;
import com.highsenbergs.ecofriendly.ui.viewmodel.ContactsViewModel;

import javax.inject.Singleton;
import dagger.Component;

@Singleton
@Component(modules = {DataModule.class})
public interface DataComponent {
    void inject(MainActivity activity);
}
