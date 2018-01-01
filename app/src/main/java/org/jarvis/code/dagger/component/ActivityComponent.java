package org.jarvis.code.dagger.component;

import org.jarvis.code.dagger.PerActivity;
import org.jarvis.code.dagger.module.ActivityModule;
import org.jarvis.code.dagger.module.PresenterModule;
import org.jarvis.code.ui.customer.RegisterFragment;
import org.jarvis.code.ui.customer.forms.BirthdayForm;
import org.jarvis.code.ui.customer.forms.CeremonyForm;
import org.jarvis.code.ui.customer.forms.HomPartyForm;
import org.jarvis.code.ui.customer.forms.InvoiceForm;
import org.jarvis.code.ui.customer.forms.WeddingForm;
import org.jarvis.code.ui.main.MainActivity;
import org.jarvis.code.ui.product.ProductFragment;
import org.jarvis.code.ui.splash.SplashActivity;

import dagger.Component;

/**
 * Created by KimChheng on 10/2/2017.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, PresenterModule.class})
public interface ActivityComponent {

    void inject(MainActivity activity);

    void inject(ProductFragment fragment);

    void inject(WeddingForm form);

    void inject(HomPartyForm form);

    void inject(BirthdayForm form);

    void inject(CeremonyForm form);

    void inject(InvoiceForm form);

    void inject(SplashActivity activity);
}
