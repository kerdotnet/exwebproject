package com.kerdotnet.command.bookcatalog;

import com.kerdotnet.entity.BookCatalog;
import com.kerdotnet.command.IActionCommand;
import com.kerdotnet.controller.SessionRequestContent;
import com.kerdotnet.exceptions.DAOSystemException;
import com.kerdotnet.exceptions.ServiceException;
import com.kerdotnet.resource.ConfigurationManager;
import com.kerdotnet.service.IBookCatalogService;
import com.kerdotnet.service.factory.ServiceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;

/**
 * List of BookCatalog - general data of books (by catalog) by authors
 * Yevhen Ivanov; 2018-04-23
 */
public class ViewBookCatalogEntityCommand implements IActionCommand {
    private static final Logger LOGGER = LoggerFactory.getLogger(ViewBookCatalogEntityCommand.class);
    private IBookCatalogService bookCatalogService;

    public ViewBookCatalogEntityCommand() {
    }

    public ViewBookCatalogEntityCommand(IBookCatalogService bookCatalogService) {
        this.bookCatalogService = bookCatalogService;
    }

    @Override
    public String execute(SessionRequestContent sessionRequestContent) throws ServletException {
        String page;

        if (bookCatalogService == null){
            try {
                ServiceFactory serviceFactory = ServiceFactory.getInstance();
                bookCatalogService = serviceFactory.getBookCatalogService();
            } catch (ServiceException|DAOSystemException e) {
                LOGGER.debug("ViewBookCatalogEntityCommand bookCatalogService init error: " + e.getMessage());
                throw new ServletException(e);
            }
        }

        BookCatalog bookCatalogEntity;
        int bookCatalogId = 0;

        String bookCatalogIdParam = sessionRequestContent.getRequestParameter("bookcatalogid");
        if (bookCatalogIdParam != null)
            bookCatalogId = Integer.parseInt(bookCatalogIdParam);
        LOGGER.debug("Id of the book catalog is: " + bookCatalogId);

        page = ConfigurationManager.getProperty("path.page.bookcatalogentity");
        try {
            sessionRequestContent.setRequestAttribute("editmode", false);
            bookCatalogEntity = bookCatalogService.getBookCatalogById(bookCatalogId);
            sessionRequestContent.setSessionAttribute("bookcatalogentity", bookCatalogEntity);
        } catch (ServiceException e) {
            throw new ServletException(e);
        }
        return page;
    }
}
