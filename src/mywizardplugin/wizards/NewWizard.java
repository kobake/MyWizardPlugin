package mywizardplugin.wizards;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;

public class NewWizard extends Wizard implements INewWizard {
	private IStructuredSelection selection;
	private NewWizardPage page1;

	public NewWizard() {
		// TODO Auto-generated constructor stub
		super();
		setNeedsProgressMonitor(true);
		setWindowTitle("新規XMLタイトル");
	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		this.selection = selection;
	}
	
	// 独自メソッド：ウィザードページの生成
	public void addPages(){
		this.page1 = new NewWizardPage("page1", this.selection);
		this.page1.setFileName("newfile.xml");
		this.page1.setTitle("新規XMLファイル");
		this.page1.setDescription("新規XMLファイルを生成します。");
		addPage(this.page1);
	}

	// 終了ボタンがクリックされたときの処理
	@Override
	public boolean performFinish() {
		IFile file = this.page1.createNewFile();
		if(file == null){
			return false;
		}
		try{
			IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
			IDE.openEditor(page, file, true);
		}
		catch(PartInitException ex){
			ex.printStackTrace();
			return false;
		}
		return true;
	}
}
