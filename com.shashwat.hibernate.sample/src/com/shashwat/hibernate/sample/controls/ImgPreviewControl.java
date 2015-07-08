package com.shashwat.hibernate.sample.controls;

import java.math.BigDecimal;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;

public class ImgPreviewControl extends Composite {

	private Label cPreview;

	private Label label;

	private int width = 10;

	private int height = 10;

	private CLabel cObservablePreview;

	private GridData imageData;

	private Image backGroundImage;

	private Image image;

	private Composite previewLevel;

	private int horiAllin;

	private int verAllin;

	public ImgPreviewControl(Composite parent, int style, int horiAllin, int verAllin) {
		super(parent, style);
		this.horiAllin = horiAllin;
		this.verAllin = verAllin;
		createPartControl();
	}

	private void createPartControl() {
		try {
			GridLayout topLayout = new GridLayout(1, true);
			topLayout.marginWidth = 0;
			topLayout.marginHeight = 0;
			topLayout.marginLeft = 0;
			topLayout.marginRight = 0;
			topLayout.marginBottom = 0;
			topLayout.marginTop = 0;
			this.setLayout(topLayout);

			if ((this.getStyle() & SWT.TITLE) != 0) {
				this.label = new Label(this, SWT.NONE);
				this.label.setLayoutData(new GridData(SWT.BEGINNING, SWT.BEGINNING, false, false));
			}
			{
				this.previewLevel = new Composite(this, SWT.BORDER);
				GridLayout previewLayout = new GridLayout(1, true);
				previewLayout.marginWidth = 0;
				previewLayout.marginHeight = 0;
				previewLayout.marginLeft = 0;
				previewLayout.marginRight = 0;
				previewLayout.marginBottom = 0;
				previewLayout.marginTop = 0;
				this.previewLevel.setLayout(previewLayout);
				this.previewLevel.setLayoutData(new GridData(SWT.FILL,
						SWT.FILL, true, true));
				{
					this.imageData = new GridData(this.horiAllin, this.verAllin, true, true);
					this.imageData.widthHint = this.width;
					this.imageData.heightHint = this.height;
					this.cPreview = new Label(this.previewLevel, SWT.NONE);
					this.cPreview.setLayoutData(this.imageData);
					this.cPreview.setAlignment(SWT.CENTER);
				}
			}
			this.addDisposeListener(new DisposeListener() {
				@Override
				public void widgetDisposed(DisposeEvent e) {
					disposeImages();
				}
			});
			this.cObservablePreview = new CLabel(getParent(), SWT.NONE) {
				@Override
				public void setImage(Image image) {
					super.setImage(image);
					ImgPreviewControl.this.setImage(image);
				}

				@Override
				public Image getImage() {
					Image img = super.getImage();
					if (img == null) {
						clearPreview();
					}
					return img;
				}
			};
			this.cObservablePreview.setVisible(false);
			GridData oData = new GridData(SWT.CENTER, SWT.CENTER, false, false);
			oData.widthHint = 0;
			oData.heightHint = 0;
			this.cObservablePreview.setLayoutData(oData);

			this.layout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void clearPreview() {
		doClearPreview();
	}

	private void doClearPreview() {
		Display.getCurrent().syncExec(new Runnable() {
			@Override
			public void run() {
				clearImage();
			}
		});
	}

	protected void clearImage() {
		try {
			Image oldImage = this.cPreview.getImage();
			if (oldImage != null && !oldImage.isDisposed()) {
				oldImage.dispose();
				oldImage = null;
			}
			if (this.image != null && !this.image.isDisposed()) {
				this.image.dispose();
				this.image = null;
			}
			Image scaledImage = scaleImage(this.backGroundImage);
			this.cPreview.setImage(scaledImage);
			this.cPreview.update();
			this.cPreview.getParent().layout();
			this.notifyListeners(SWT.Modify, createEvent());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Image scaleImage(Image previewImage) {
		try {
			if (previewImage == null)
				return null;
			Rectangle r = previewImage.getBounds();
			if (r.width * r.height == 0)
				return previewImage;
			if (r.width < this.width && r.height < this.height) {
				GridData gd = (GridData) this.cPreview.getLayoutData();
				gd.widthHint = this.width;
				gd.heightHint = this.height;
				return previewImage;
			}
			double pw = new BigDecimal(this.width).doubleValue();
			double ph = new BigDecimal(this.height).doubleValue();
			double nw = pw;
			double nh = ph;
			double rw = new BigDecimal(r.width).doubleValue();
			double rh = new BigDecimal(r.height).doubleValue();
			double pk = pw / ph;
			double ik = rw / rh;
			if (pk < ik) {
				nh = (pw * ph * rh) / (ph * rw);
			} else if (pk > ik) {
				nw = (pw * ph * rw) / (pw * rh);
			}
			this.imageData.widthHint = (new BigDecimal(nw)).intValue();
			this.imageData.heightHint = (new BigDecimal(nh)).intValue();
			Image scaledImage = new Image(getDisplay(), previewImage
					.getImageData().scaledTo(this.imageData.widthHint,
							this.imageData.heightHint));
			return scaledImage;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return previewImage;
	}

	public void setImage(Image previewImage) {
		if (this.image != null && !this.image.isDisposed()) {
			this.image.dispose();
		}
		this.image = new Image(Display.getDefault(), previewImage,
				SWT.IMAGE_COPY);
		this.notifyListeners(SWT.Modify, createEvent());
		doPreview();
	}
	
	public Image getImage() {
		if (this.image != null && !this.image.isDisposed()) {
			return this.image;
		}
		return null;
	}

	private Event createEvent() {
		Event event = new Event();
		event.widget = this;
		event.type = SWT.Modify;
		event.data = this.image;
		return event;
	}

	private void doPreview() {
		Display.getCurrent().syncExec(new Runnable() {
			@Override
			public void run() {
				previewImage();
			}
		});
	}

	protected void previewImage() {
		try {
			Image oldImage = this.cPreview.getImage();
			if (oldImage != null && !oldImage.isDisposed()) {
				oldImage.dispose();
			}
			if (this.image != null && !this.image.isDisposed()) {
				Image myScaledImage = scaleImage(this.image);
				this.cPreview.setImage(myScaledImage);
				this.cPreview.update();
				this.cPreview.getParent().layout();
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		clearImage();
	}

	public void setPreviewBounds(Rectangle r) {
		if (r != null) {
			this.width = r.width;
			this.height = r.height;
		}
		doPreview();
	}

	public void setBackgroundImage(Image previewImage) {
		if (this.backGroundImage != null && !this.backGroundImage.isDisposed()) {
			this.backGroundImage.dispose();
		}
		this.backGroundImage = new Image(Display.getDefault(), previewImage,
				SWT.IMAGE_COPY);
	}

	public void setBackground(Color color) {
		if (this.label != null) {
			this.label.setBackground(color);
		}
		this.cPreview.setBackground(color);
		this.previewLevel.setBackground(color);
		super.setBackground(color);
	}

	public void setForeground(Color color) {
		if (this.label != null) {
			this.label.setForeground(color);
		}
		this.cPreview.setForeground(color);
		this.previewLevel.setForeground(color);
		super.setForeground(color);
	}

	protected void disposeImages() {
		if (this.image != null && !this.image.isDisposed()) {
			this.image.dispose();
		}
		if (this.backGroundImage != null && !this.backGroundImage.isDisposed()) {
			this.backGroundImage.dispose();
		}
	}

	public void addModifyListener(Listener listener) {
		this.addListener(SWT.Modify, listener);
	}

	public void removeModifyListener(Listener listener) {
		this.removeListener(SWT.Modify, listener);
	}
}
