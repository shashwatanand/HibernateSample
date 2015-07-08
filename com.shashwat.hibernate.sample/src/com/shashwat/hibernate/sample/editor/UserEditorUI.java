package com.shashwat.hibernate.sample.editor;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IFormColors;
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.part.EditorPart;

import com.shashwat.hibernate.sample.Activator;
import com.shashwat.hibernate.sample.controls.ImgPreviewControl;

public abstract class UserEditorUI extends EditorPart {
	private static final int AVATAR_WIDTH = 99;
	private static final int AVATAR_HEIGHT = 120;

	protected Form form;
	private Composite mainComposite;
	protected Text txtName;
	protected Text txtAddress;
	protected Text txtEmail;
	protected Text txtMobile;
	protected Text txtPhone;
	protected Button saveButton;
	protected ImgPreviewControl preview;
	protected Button bSelectPhoto;

	@Override
	public void createPartControl(Composite parent) {
		FormToolkit toolkit = new FormToolkit(parent.getDisplay());
		Color c = parent.getDisplay().getSystemColor(
				SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT);
		toolkit.getColors().createColor(IFormColors.SEPARATOR, c.getRed(),
				c.getGreen(), c.getBlue());
		this.form = toolkit.createForm(parent);

		toolkit.decorateFormHeading(this.form);
		this.form.setImage(this.getTitleImage());
		this.setPartName(this.getEditorInput().getName());
		GridLayout formLayout = new GridLayout(1, true);
		formLayout.marginHeight = 0;
		Composite body = this.form.getBody();
		body.setLayout(formLayout);
		this.form.setText(this.getEditorInput().getName());
		this.mainComposite = toolkit.createComposite(body);
		this.mainComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
				true));
		GridLayout layout = new GridLayout(2, false);
		layout.horizontalSpacing = 3;
		layout.verticalSpacing = 3;
		layout.marginWidth = 0;
		this.mainComposite.setLayout(layout);

		Composite leftComp = toolkit.createComposite(this.mainComposite);
		leftComp.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		GridLayout leftComplayout = new GridLayout(2, false);
		layout.horizontalSpacing = 3;
		layout.verticalSpacing = 3;
		layout.marginWidth = 0;
		leftComp.setLayout(leftComplayout);

		Composite rightComp = toolkit.createComposite(this.mainComposite);
		rightComp.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
		GridLayout rightComplayout = new GridLayout(1, false);
		layout.horizontalSpacing = 5;
		layout.verticalSpacing = 5;
		layout.marginWidth = 0;
		rightComp.setLayout(rightComplayout);

		Label lName = toolkit.createLabel(leftComp, "Name", SWT.NONE);
		GridData lNameLData = new GridData(SWT.BEGINNING, SWT.BEGINNING, false,
				false);
		lName.setLayoutData(lNameLData);

		this.txtName = toolkit.createText(leftComp, "", SWT.BORDER); //$NON-NLS-1$
		GridData tNameLData = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
		this.txtName.setLayoutData(tNameLData);

		Label address = toolkit.createLabel(leftComp, "Address", SWT.NONE);
		GridData addressData = new GridData(SWT.BEGINNING, SWT.BEGINNING,
				false, false);
		address.setLayoutData(addressData);

		this.txtAddress = toolkit.createText(leftComp, "", SWT.BORDER); //$NON-NLS-1$
		GridData tAddressData = new GridData(SWT.FILL, SWT.BEGINNING, true,
				false);
		this.txtAddress.setLayoutData(tAddressData);

		Label email = toolkit.createLabel(leftComp, "Email", SWT.NONE);
		GridData emailData = new GridData(SWT.BEGINNING, SWT.BEGINNING, false,
				false);
		email.setLayoutData(emailData);

		this.txtEmail = toolkit.createText(leftComp, "", SWT.BORDER); //$NON-NLS-1$
		GridData txtEmailData = new GridData(SWT.FILL, SWT.BEGINNING, true,
				false);
		this.txtEmail.setLayoutData(txtEmailData);
		/*this.txtEmail.addVerifyListener(new VerifyListener() {
			
			@Override
			public void verifyText(VerifyEvent event) {
				event.doit = false;
				String text = ((Text) event.widget).getText();
				if (text.matches("^[a-zA-Z0-9._%+-]+@(?:[a-zA-Z0-9-]+\\.)+[A-Za-z]{2,4}$")) { //$NON-NLS-1$
					event.doit = true;
				}
			}
		});*/

		Label phone = toolkit.createLabel(leftComp, "Phone", SWT.NONE);
		GridData phoneData = new GridData(SWT.BEGINNING, SWT.BEGINNING, false,
				false);
		phone.setLayoutData(phoneData);

		this.txtPhone = toolkit.createText(leftComp, "", SWT.BORDER); //$NON-NLS-1$
		GridData txtPhoneData = new GridData(SWT.FILL, SWT.BEGINNING, true,
				false);
		this.txtPhone.setLayoutData(txtPhoneData);

		Label mobile = toolkit.createLabel(leftComp, "Mobile", SWT.NONE);
		GridData mobileData = new GridData(SWT.BEGINNING, SWT.BEGINNING, false,
				false);
		mobile.setLayoutData(mobileData);

		this.txtMobile = toolkit.createText(leftComp, "", SWT.BORDER); //$NON-NLS-1$
		GridData txtMobileData = new GridData(SWT.FILL, SWT.BEGINNING, true,
				false);
		this.txtMobile.setLayoutData(txtMobileData);

		toolkit.createLabel(leftComp, "", SWT.NONE);

		this.saveButton = toolkit.createButton(leftComp, "Save", SWT.PUSH);
		GridData saveButtonData = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
		this.saveButton.setLayoutData(saveButtonData);
		
		this.saveButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				saveData();
			}
		});

		this.preview = new ImgPreviewControl(rightComp, SWT.NONE, SWT.CENTER, SWT.TOP);	
		
		GridData canvasMemberImageLData = new GridData(SWT.FILL, SWT.TOP,
				true, false);
		canvasMemberImageLData.widthHint = AVATAR_WIDTH;
		canvasMemberImageLData.heightHint = AVATAR_HEIGHT;
		this.preview.setLayoutData(canvasMemberImageLData);
		toolkit.adapt(this.preview);
		this.preview.setBackgroundImage(Activator.getImageDescriptor("icons/avatar_photo.png").createImage());
		this.preview.setPreviewBounds(new Rectangle(0, 0, AVATAR_WIDTH, AVATAR_HEIGHT));
		
		this.bSelectPhoto = toolkit.createButton(rightComp, "SelectPhoto", SWT.PUSH | SWT.CENTER);
        GridData bSelectPhotoLData = new GridData(SWT.CENTER, SWT.BEGINNING, false, false);
        this.bSelectPhoto.setLayoutData(bSelectPhotoLData);
        
        this.bSelectPhoto.addSelectionListener(new SelectionAdapter() {
        	@Override
        	public void widgetSelected(SelectionEvent e) {
        		selectPhoto();
        	}
		});
	}
	
	abstract protected void selectPhoto();
	
	abstract protected void saveData();
}
