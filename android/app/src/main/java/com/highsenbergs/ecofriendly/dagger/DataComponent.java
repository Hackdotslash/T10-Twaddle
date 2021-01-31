package com.highsenbergs.ecofriendly.dagger;

import com.highsenbergs.ecofriendly.ui.activities.MainActivity;
import com.highsenbergs.ecofriendly.ui.activities.UserInfoActivity;
import com.highsenbergs.ecofriendly.ui.fragments.HomeFragment.FriendsDrive;
import com.highsenbergs.ecofriendly.ui.fragments.HomeFragment.SoloDrive;
import com.highsenbergs.ecofriendly.ui.fragments.SocialFragment.FriendsLeaderboardFragment;
import com.highsenbergs.ecofriendly.ui.fragments.SocialFragment.OverviewFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {DataModule.class})
public interface DataComponent {
    void inject(MainActivity activity);
    void inject(UserInfoActivity userInfoActivity);
    void inject(SoloDrive soloDrive);
    void inject(FriendsDrive friendsDrive);
    void inject(OverviewFragment overviewFragment);
    void inject(FriendsLeaderboardFragment friendsLeaderboardFragment);

}
