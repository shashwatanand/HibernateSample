package com.shashwat.hibernate.sample.editor;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;

import com.shashwat.hibernate.sample.datalayer.DataLayer;
import com.shashwat.hibernate.sample.model.Users;

public class UserEditor extends UserEditorUI {
	public final static String ID = "com.shashwat.hibernate.sample.editor.UserEditor";

	@Override
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
		setSite(site);
		if (input != null) {
			setInput(input);
		}
	}

	@Override
	public void createPartControl(Composite parent) {
		super.createPartControl(parent);
		// addValidation();
	}

	/*
	 * private void addValidation() { IValidator eMailValidator = new
	 * IValidator() {
	 * 
	 * @Override public IStatus validate(Object value) { String s =
	 * String.valueOf(value); if
	 * (s.matches("^[a-zA-Z0-9._%+-]+@(?:[a-zA-Z0-9-]+\\.)+[A-Za-z]{2,4}$")) {
	 * //$NON-NLS-1$ return ValidationStatus.ok(); } return
	 * ValidationStatus.error("Enter correct email"); } };
	 * this.eMailUpdateStrategy = new UpdateValueStrategy();
	 * this.eMailUpdateStrategy.setBeforeSetValidator(eMailValidator); }
	 */

	@Override
	public void doSave(IProgressMonitor monitor) {
		// TODO Auto-generated method stub
	}

	@Override
	public void doSaveAs() {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean isDirty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isSaveAsAllowed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub
	}

	@Override
	protected void selectPhoto() {
		FileDialog dialog = new FileDialog(this.bSelectPhoto.getShell(),
				SWT.OPEN);
		dialog.setText("Open");
		String[] filterExt = { "*.*", "*.jpg", "*.jpeg", "*.png", "*.bmp" };
		dialog.setFilterExtensions(filterExt);
		String filePath;
		if ((filePath = dialog.open()) != null && !filePath.isEmpty()) {
			this.preview.setImage(new Image(Display.getDefault(), filePath));
		}
	}

	@Override
	protected void saveData() {
		Users user = new Users();
		String name;
		String address;
		String email;
		String phone;
		String mobile;
		Image image;
		if ((name = this.txtName.getText()) != null && !name.isEmpty()) {
			user.setName(name);
			if ((address = this.txtAddress.getText()) != null) {
				user.setAddress(address);
			}

			if ((email = this.txtEmail.getText()) != null) {
				user.setEmail(email);
			}

			if ((phone = this.txtPhone.getText()) != null) {
				user.setPhone(phone);
			}

			if ((mobile = this.txtMobile.getText()) != null) {
				user.setMobile(mobile);
			}
			if ((image = this.preview.getImage()) != null) {
				user.setBitmap(image.getImageData().data);
			}
			
			DataLayer.saveUser(user);
			//DataLayer.listUsers();
			
			clearFields();
		}
		//DataLayer.listUsers();
	}
	
	public void clearFields() {
		this.txtName.setText("");
		this.txtAddress.setText("");
		this.txtEmail.setText("");
		this.txtPhone.setText("");
		this.txtMobile.setText("");
		this.preview.clearPreview();
	}
}
